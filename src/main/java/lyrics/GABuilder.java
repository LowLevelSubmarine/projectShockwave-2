package lyrics;

import com.github.scribejava.apis.GeniusApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;

public class GABuilder {
    private String clientId;
    private String accessToken;
    private String userAgent;

    public GABuilder(String clientId, String accessToken, String userAgent) {
        this.clientId = clientId;
        this.accessToken = accessToken;
        this.userAgent = userAgent;
    }

    public GA build() {
        OAuth20Service service = new ServiceBuilder(this.clientId).userAgent(this.userAgent).build(GeniusApi.instance());
        return new GA(service, this.accessToken);
    }
}
