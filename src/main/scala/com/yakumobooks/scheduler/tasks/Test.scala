package com.yakumobooks.scheduler.tasks

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class Test {

  @Scheduled(cron="${task.cron}")
  def test = {
    println("Hello World.")
  }

}
