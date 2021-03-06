package proto.actor

class ActorClient : SenderContext {
    private var _senderMiddleware: ((SenderContext, PID, MessageEnvelope) -> Unit)? = null

    constructor(messageHeader: MessageHeader, middleware: Array<((SenderContext, PID, MessageEnvelope) -> Unit) -> (SenderContext, PID, MessageEnvelope) -> Unit>) {
        //_senderMiddleware = defaultSender, {inner, outer -> outer(inner)})
        headers = messageHeader
    }

    override val message: Any?
        get() = null
    override val headers: MessageHeader
    private fun defaultSender(context: SenderContext, target: PID, message: MessageEnvelope): Unit {
        target.tell(message)
    }

    fun tell(target: PID, message: Any) {
        if (_senderMiddleware != null) {
            if (message is MessageEnvelope) {
                _senderMiddleware!!.invoke(this, target, message)
            } else {
                _senderMiddleware!!.invoke(this, target, MessageEnvelope(message, null, null))
            }
        } else {
            target.tell(message)
        }
    }

    fun request(target: PID, message: Any, sender: PID) {
        val envelope: MessageEnvelope = MessageEnvelope(message, sender, null)
        tell(target, envelope)
    }

//    suspend fun <T> requestAsync(target: PID, message: Any, timeout: Duration): T {
//        throw Exception()
//    }
//
//    suspend fun <T> requestAsync(target: PID, message: Any): T {
//        throw Exception()
//    }
}

