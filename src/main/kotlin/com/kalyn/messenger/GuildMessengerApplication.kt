package com.kalyn.messenger

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GuildMessengerApplication

fun main(args: Array<String>) {
	runApplication<GuildMessengerApplication>(*args)
}
