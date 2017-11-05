package io.tapack.satisfy.rest.fake;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class UserStorage {

    private List<User> users = new ArrayList<>();

    public UserStorage() {
        User John = new User();
        John.setEmail("john@hascode.com");
        John.setFirstName("John");
        John.setLastName("Testerman");
        John.setId(1L);
        users.add(John);

        User marta = new User();
        marta.setEmail("marta@hascode.com");
        marta.setFirstName("Marta");
        marta.setLastName("Stevens");
        marta.setId(2L);
        users.add(marta);

        User mark = new User();
        mark.setEmail("mark@hascode.com");
        mark.setFirstName("Mark");
        mark.setLastName("Mustache");
        mark.setId(3L);
        users.add(mark);
    }

    public List<User> getUsers() {
        return users;
    }

    public User findUserById(int id) {
        for (User user : users) {
            if (user.getId() == id) return user;
        }
        return new User();
    }

    public void updateUserById(Long id, String firstName, String lastName) {
        for (User user : users) {
            if (Objects.equals(user.getId(), id)) {
                user.setFirstName(firstName);
                user.setLastName(lastName);
            }
        }
    }

    public void addUser(long id, String firstName, String lastName, String email) {
        User user = new User();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        users.add(user);
    }

    public void deleteUserById(int id) {
        Iterator<User> userIterator = users.iterator();
        while (userIterator.hasNext()) {
            if (userIterator.next().getId() == id) {
                userIterator.remove();
            }
        }
    }

    public void addUser(User user) {
        users.add(user);
    }
}
