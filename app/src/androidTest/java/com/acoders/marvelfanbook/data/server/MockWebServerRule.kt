package com.acoders.marvelfanbook.data.server

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class MockWebServerRule : TestWatcher() {

    lateinit var server: MockWebServer

    override fun starting(description: Description) {
        server = MockWebServer().apply {
            dispatcher = object : Dispatcher() {
                override fun dispatch(request: RecordedRequest): MockResponse {
                    val path = request.path

                    return if (path != null) {
                        when {
                            path.contains("series") -> MockResponse().fromJson("superheroes.json")
                            else -> MockResponse()
                        }
                    } else {
                        MockResponse()
                    }
                }
            }
        }

        server.start(8080)
    }

    override fun finished(description: Description) {
        server.shutdown()
    }
}