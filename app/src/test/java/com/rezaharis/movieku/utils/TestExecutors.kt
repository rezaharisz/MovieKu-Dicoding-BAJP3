package com.rezaharis.movieku.utils

import java.util.concurrent.Executor

class TestExecutors: Executor {
    override fun execute(command: Runnable) {
        command.run()
    }
}