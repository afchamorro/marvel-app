package com.acoders.marvelfanbook.core.exception

sealed class Failure {
    object NetworkConnection : Failure()
    object ServerError : Failure()
    object BaseDataError : Failure()
    object UpdateRequired : Failure()
    object GeneralError : Failure()
    object ToManyConnections : Failure()
    object Unauthorized : Failure()
    object NotAcceptable : Failure()
    object Forbidden : Failure()

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure : Failure()
}
