package quickcheck

import common._

import org.scalacheck._
import Arbitrary._
import Gen._
import Prop._

abstract class QuickCheckHeap extends Properties("Heap") with IntHeap {

  property("min1") = forAll { a: Int =>
    val h = insert(a, empty)
    findMin(h) == a
  }

  property("min1 when 2 values inserted") = forAll { (a: Int, b : Int) =>
    val m = math.min(a, b)
    val h = insert(a, empty)
    val h2 = insert(b, h)
    findMin(h2) == m
  }

  property("merge of 2 heaps") = forAll { (a1 : Int, a2: Int, a3: Int, a4: Int, a5 : Int) =>
    val l = a1::a2::a3::a4::a5::Nil sorted
    val m = l.head
    val h = insert(a3, insert(a2, insert(a1, empty)))
    val h2 = insert(a5, insert(a4, empty))
    findMin(meld(h2, h)) == m
  }

  property("delete minimum in single element heap") = forAll { a: Int =>
    val h = insert(a, empty)
    val eh = deleteMin(h)
    eh == Nil
  }
  
  property("delete minimum 2 times") = forAll { (a: Int, b : Int, c : Int) =>
    val l = (a :: b :: c :: Nil) sorted
    val h = insert(c, insert(b, insert(a, empty)))
    val eh = deleteMin(deleteMin(h))
    findMin(eh) == l.drop(2).head
  }

  property("duplicate  for github testing delete minimum 2 times") = forAll { (a: Int, b : Int, c : Int) =>
    val l = (a :: b :: c :: Nil) sorted
    val h = insert(c, insert(b, insert(a, empty)))
    val eh = deleteMin(deleteMin(h))
    findMin(eh) == l.drop(2).head
  }
  
  property("delete minimum should return value in sorted order") = forAll { (a1 : Int, a2: Int, a3: Int, a4: Int, a5: Int,a6: Int,a7: Int,a8: Int) =>
    val l = a1::a2::a3::a4::a5::a6::a7::a8::Nil
    val heap = l.foldLeft(empty)((h, e) => insert(e, h))
    val sortedL = l sorted
    
    def compare(m : List[Int], h : H) : Boolean = {
      
      if (m.isEmpty && isEmpty(h)) {
        true
      } else {
        val fm = findMin(h)
    	m.head == fm
    	compare(m.tail, deleteMin(h))
      }  
    } 
    compare(sortedL, heap)
  }

  // lazy val genHeap: Gen[H] = ???
  lazy val genHeap: Gen[H] = for {
	v <- arbitrary[Int]
    h <- arbitrary[H]
  } yield insert(v, h)

  implicit lazy val arbHeap: Arbitrary[H] = Arbitrary(genHeap)

}
