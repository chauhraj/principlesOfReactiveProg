Principles of Reactive Programming
==================================

Monad is a type constructor M[T] that adheres to following algebraic laws. Besides, every Monad supports two operations flatMap(equivalent to bind in Haskell), and unit.

Monad Laws
----------

* Left Identity

```scala   
   unit(x) flatMap f == f(x)
```   
```scala
trait Option[+A] {

  // from scala library
  @inline final def flatMap[B](f: A => Option[B]): Option[B] =
    if (isEmpty) None else f(this.get)
    
}

```
* Right Identity

```scala   
   m flatMap unit == m
```   
* Associativity

```   
   (m flatMap f) flatMap g == m flatMap ( x => f(x) flatMap g )
```   



Notes
=====

* [Monadic Laws](http://www.haskell.org/haskellwiki/Monad_laws)

