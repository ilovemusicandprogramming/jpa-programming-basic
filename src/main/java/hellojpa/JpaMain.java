package hellojpa;

import jakarta.persistence.*;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // 값을 복사해서 사용해야함 ! 공유하는 것은 위험 -> 생성자로만 값 접근해야함
            // ** 불변이라는 작은 제약으로 부작용이라는 큰 재앙을 막을수 있다. **
            Address address = new Address("city", "street", "1000");

            Member member = new Member();
            member.setUsername("member1");
            member.setHomeAddress(address);
            em.persist(member);

            // 수정하려면 이렇케 통으로 바꿔야함
            Address newAddress = new Address("New City", address.getStreet(), address.getZipcode());
            member.setHomeAddress(newAddress);
            
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }
}
