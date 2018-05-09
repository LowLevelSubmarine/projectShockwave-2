package commands.administration;

import commands.CommandInfo;
import commands.CommandInterface;
import commands.SecurityLevel;
import tools.Toolkit;
import fr.bmartel.speedtest.SpeedTestReport;
import fr.bmartel.speedtest.SpeedTestSocket;
import fr.bmartel.speedtest.inter.ISpeedTestListener;
import fr.bmartel.speedtest.model.SpeedTestError;
import fr.bmartel.speedtest.model.SpeedTestMode;
import messages.MsgBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.TextChannel;

import java.math.BigDecimal;

public class Speedtest implements CommandInterface, ISpeedTestListener {

    private static final String downloadURI = "http://2.testdebit.info/5M.iso";
    private static final String uploadURI = "http://2.testdebit.info/";
    private static final int uploadInterval = 1000000;

    private TextChannel textChannel;
    private Message message = null;
    private SpeedTestSocket speedtest = new SpeedTestSocket();
    private String downloadProgress = null;
    private String uploadProgress = null;

    @Override
    public SecurityLevel securityLevel() {
        return SecurityLevel.BOT;
    }

    @Override
    public void run(CommandInfo info) {
        this.textChannel = info.getChannel();
        this.speedtest.addSpeedTestListener(this);
        this.speedtest.startDownload(downloadURI);
        updateMessage();
    }

    @Override
    public void onCompletion(SpeedTestReport report) {
        if (report.getSpeedTestMode() == SpeedTestMode.DOWNLOAD) {
            this.downloadProgress = getMbits(report.getTransferRateBit());
            this.speedtest.startUpload(uploadURI, uploadInterval);
            updateMessage();
        }
        if (report.getSpeedTestMode() == SpeedTestMode.UPLOAD) {
            this.uploadProgress = getMbits(report.getTransferRateBit());
            updateMessage();
        }
    }

    @Override
    public void onProgress(float percent, SpeedTestReport report) {
    }

    @Override
    public void onError(SpeedTestError speedTestError, String errorMessage) {
        System.out.println(errorMessage);
    }

    @Override
    public String category() {
        return "Administration";
    }

    @Override
    public String title() {
        return "Führt einen Speedtest durch";
    }

    @Override
    public String description() {
        return "Testet die Internetgeschwindigkeit des Hosts";
    }

    @Override
    public String syntax(String p) {
        return p + "speedtest";
    }



    private void updateMessage() {
        MessageEmbed embed = MsgBuilder.speedtestProgress(this.downloadProgress, this.uploadProgress);
        if (this.message == null) {
            this.message = this.textChannel.sendMessage(embed).complete();
        } else {
            this.message.editMessage(embed).queue();
        }
        this.message.editMessage(embed).queue();
    }

    private static String getMbits(BigDecimal decimal) {
        return Toolkit.round(decimal.doubleValue() / 1024 / 1024, 2) + " MBit/s";
    }
}
