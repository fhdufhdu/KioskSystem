package com.fhdufhdu.kiosk

//import com.fhdufhdu.kiosk.auth.StoreUserDetailsService
import com.fhdufhdu.kiosk.auth.LoginFilter
import com.fhdufhdu.kiosk.common.KioskPasswordEncoder
import com.fhdufhdu.kiosk.repository.StoreRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@ComponentScan
@EnableWebSecurity
class SpringConfiguration(
//    val storeUserDetailsService: StoreUserDetailsService,
    val authenticationConfiguration: AuthenticationConfiguration,
    val storeRepository: StoreRepository
) {

    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .formLogin {
                it.disable()
            }
            .httpBasic {
                it.disable()
            }
            .csrf {
                it.disable()
            }
            .addFilterBefore(
                LoginFilter(kioskPasswordEncoder(), storeRepository),
                UsernamePasswordAuthenticationFilter::class.java
            )
            .authorizeHttpRequests {
                it.requestMatchers("/store/sign-in", "/store/sign-up", "/error").permitAll()
                    .anyRequest().authenticated()
            }
//            .authenticationProvider(authProvider())

        return http.build()
    }

    @Bean
    fun authenticationManager(): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return kioskPasswordEncoder()
    }

    @Bean
    fun kioskPasswordEncoder(): KioskPasswordEncoder {
        return KioskPasswordEncoder()
    }

//    @Bean
//    fun authProvider(): DaoAuthenticationProvider {
//        val authProvider = DaoAuthenticationProvider()
//        authProvider.setUserDetailsService(storeUserDetailsService)
//        authProvider.setPasswordEncoder(passwordEncoder())
//        return authProvider
//    }

}