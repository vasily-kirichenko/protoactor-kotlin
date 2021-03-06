package proto.actor

import proto.mailbox.Dispatcher
import java.util.*

class EventSubscription<T>(val eventStream: EventStreamImpl<T>, val dispatcher: Dispatcher, val action: (T) -> Unit) {
    val id: UUID = UUID.randomUUID()
    fun unsubscribe() {
        eventStream.unsubscribe(id)
    }
}