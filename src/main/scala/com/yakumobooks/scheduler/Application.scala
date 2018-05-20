package com.yakumobooks.scheduler

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.annotation.PropertySource
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
@PropertySource(value=Array("classpath:/application-${spring.profiles.active}.properties"))
class Application

object Application {
  def main(args:Array[String]): Unit = {
    (new SpringApplicationBuilder)
      .bannerMode(Banner.Mode.OFF)
      .sources(classOf[Application])
      .web(false)
      .headless(true)
      .run(args:_*)
  }
}
