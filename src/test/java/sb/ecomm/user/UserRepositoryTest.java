package sb.ecomm.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest(
        properties = {"spring.jpa.properties.javax.persistence.validation.mode=none"}
)
class UserRepositoryTest {

    @Autowired
    private UserRepository underTest;

    @Test
    void doesNotSaveUserWhenUsernameIsNull() {
        User user = new User();
        user.setEmail("test@test.com");
        user.setPassword("test123");

        assertThatThrownBy(() -> underTest.save(user))
                .hasMessageContaining("not-null property references a null or transient value : sb.ecomm.user.User.username")
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void doesNotSaveUserWhenEmailIsNull() {
        User user = new User();
        user.setUsername("Test");
        user.setPassword("test123");

        assertThatThrownBy(() -> underTest.save(user))
                .hasMessageContaining("not-null property references a null or transient value : sb.ecomm.user.User.email")
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void doesNotSaveUserWhenPasswordIsNull() {
        User user = new User();
        user.setUsername("Test");
        user.setEmail("test@test.com");

        assertThatThrownBy(() -> underTest.save(user))
                .hasMessageContaining("not-null property references a null or transient value : sb.ecomm.user.User.password")
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void findsUserByEmail() {
        User user = new User();
        user.setUsername("Test");
        user.setEmail("test@test.com");
        user.setPassword("test123");

        underTest.save(user);

        Optional<User> optionalUser = underTest.findByEmail("test@test.com");

        assertThat(optionalUser).isPresent().hasValueSatisfying(u -> assertThat(u).isEqualTo(user));
    }

    @Test
    void doesNotFindUserByEmailWhenEmailDoesNotExist() {
        User user = new User();
        user.setUsername("Test");
        user.setEmail("test@test.com");
        user.setPassword("test123");

        underTest.save(user);

        Optional<User> optionalUser = underTest.findByEmail("testfail@test.com");

        assertThat(optionalUser).isNotPresent();
    }

    @Test
    void findsUserByVerificationHash() {
        String hash = "abcde12345";
        User user = new User();
        user.setUsername("Test");
        user.setEmail("test@test.com");
        user.setPassword("test123");
        user.setVerificationHash(hash);

        underTest.save(user);

        Optional<User> optionalUser = underTest.findByVerificationHash(hash);

        assertThat(optionalUser).isPresent().hasValueSatisfying(u -> assertThat(u).isEqualTo(user));
    }

    @Test
    void doesNotFindUserByVerificationHashWhenVerificationHashDoesNotExist() {
        User user = new User();
        user.setUsername("Test");
        user.setEmail("test@test.com");
        user.setPassword("test123");

        underTest.save(user);

        Optional<User> optionalUser = underTest.findByVerificationHash("abcdef1234");

        assertThat(optionalUser).isNotPresent();
    }
}