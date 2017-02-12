package ch

import org.apache.kafka.streams.kstream.{ValueMapper, KeyValueMapper}
import scala.language.implicitConversions

package object theza {

  implicit def functionToValueMapper[V1, V2](f: V1 => V2): ValueMapper[V1, V2] = 
    new ValueMapper[V1, V2] {
      override def apply(value: V1): V2 = f(value)
    }

  implicit def functionToKeyValueMapper[K, V, R](f: (K, V) => R): KeyValueMapper[K, V, R] =
    new KeyValueMapper[K, V, R] {
      override def apply(key: K, value: V): R = f(key, value)
    }
    
}
