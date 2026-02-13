package elms.config;


import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAI(){
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                //API Information
                .info(new Info()
                        .title("Employee Leave Management API")
                        .description("REST API for managing employee leave requests, " + "approvals, and tracking leave balances.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Jaya Krishna")
                                .email("polakijayakrishn@gmail.com")))
                // 2. TELL SWAGGER: "Every endpoint needs a Bearer token"
                //To use ANY endpoint here, you need something called bearerAuth
                .addSecurityItem(new SecurityRequirement()
                        .addList(securitySchemeName)) //securitySchemeName = "bearerAuth"

                // 3. DEFINE WHAT "bearerAuth" MEANS
                //Think of Components as a storage box
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,//"bearerAuth" — the sticker name
                                new SecurityScheme()            // The ACTUAL instructions
                                        .name(securitySchemeName) // "bearerAuth"
                                        .type(SecurityScheme.Type.HTTP)//HTTP
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("Paste your JWT token here (without 'Bearer ' prefix)")));
    }
}
