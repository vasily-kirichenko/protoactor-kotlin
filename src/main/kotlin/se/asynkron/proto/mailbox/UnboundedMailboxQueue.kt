package proto.mailbox

import java.util.concurrent.ConcurrentLinkedQueue

class UnboundedMailboxQueue : IMailboxQueue {
    private val messages: ConcurrentLinkedQueue<Any> = ConcurrentLinkedQueue()
    override fun push(message: Any) {
        messages.add(message)
    }

    override fun pop(): Any? {
        return messages.poll()
    }

    override val hasMessages: Boolean
        get() = messages.count() > 0
}

