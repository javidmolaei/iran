package ir.javid.iran.config;

import ir.javid.iran.helper.CustomOAuth2User;
import ir.javid.iran.helper.CustomOAuth2UserService;
import ir.javid.iran.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

/**
 * author: Mr.javidmolaei
 */

@Configuration
@AllArgsConstructor
public class SpringSecurityConfig {

    private UserDetailsService userDetailsService;
    private UserService userService;

    @Autowired
    private CustomOAuth2UserService oauthUserService;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/", "/css/**","/images/**","/fonts/**", "/login").permitAll()
                        .requestMatchers("/icon-fonts/**").permitAll()
                        .requestMatchers("/js/**").permitAll()
                        .requestMatchers("/uploaddd/**").permitAll()
                        .requestMatchers("/libs/**").permitAll()
                        .requestMatchers("/video/**").permitAll()
                        .requestMatchers("/register").permitAll()
                        .requestMatchers("/success/**").permitAll()
                        .requestMatchers("/unsuccess/**").permitAll()
                        .requestMatchers("/successcode/**").permitAll()
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/error/**").permitAll()
                        .requestMatchers("/users/**").hasAuthority("SUPER_ADMIN")
                        .requestMatchers("/register/**").hasAnyAuthority("SUPER_ADMIN","MARKETER")
                        .anyRequest().authenticated()
                )
//                .httpBasic(Customizer.withDefaults())
                .formLogin(
                        form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/panel")
                                .permitAll()
                ).logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()
                ).oauth2Login()
                .loginPage("/login")
                .userInfoEndpoint()
                .userService(oauthUserService).and()
                .successHandler(new AuthenticationSuccessHandler() {

                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                        Authentication authentication) throws IOException, ServletException {

                        CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();

                        userService.processOAuthPostLogin(oauthUser.getEmail());

                        response.sendRedirect("/panel");
                    }
                });
        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


}
