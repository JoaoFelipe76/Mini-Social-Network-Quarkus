package config;

import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;

@OpenAPIDefinition(info = @Info(
        title="mini-social-network-quarkus API",
        version = "1.0.1",
        contact = @Contact(
                name = "mini-social-network-quarkus API",
                url = "https://github.com/JoaoFelipe76",
                email = "joaof.lorenzon@outlook.com"),
        license = @License(
                name = "Apache 2.0",
                url = "https://www.apache.org/licenses/LICENSE-2.0.html")))
public class SwaggerConfig extends Application {
}
