package vn.com.openlab.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "OpenLAB",
                        email = "openlab.user@gmail.com",
                        url = "www.openlab.com.vn"
                ),
                description = "Open Api documentation for OpenLAB App",
                title = "Open Api OpenLAB App (E-Ecommerce)",
                version = "0.0.1-SNAPSHOT",
                license = @License(
                        name = "Apache License Version 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0"
                ),
                termsOfService = "Term of service"
        ),
        servers = {
                @Server(
                        description = "Swagger Environment",
                        url = "http://14.225.255.177:8081"
                ),
                @Server(
                        description = "Pro Environment",
                        url = "https://openlab.com.vn"
                ),
                @Server(
                        description = "Pro Environment",
                        url = "http://localhost:8081"
                ),
                @Server(
                        description = "Pro Environment",
                        url = "http://localhost:3000"
                ),
        },
        security = {
                @SecurityRequirement(
                        name = "BearerAuth"
                )
        }
)
@SecurityScheme(
        name = "BearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
@Configuration
public class OpenApiConfig {
}
