package service;

import java.util.List;

import javax.ejb.DependsOn;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import model.User;
import repository.RepositoryException;
import repository.UserRepository;
import utils.PasswordEncryptor;

@Stateless(name = "UserService", mappedName = "UserService")
@DependsOn({ "UserRepository" })
@Local
public class UserServiceBean implements UserService {

	@EJB(name = "UserRepository")
	UserRepository repo;

	public List<User> getAll() {
		try {
			return repo.getAll();
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public User getUser(Long id) {
		try {
			return repo.getById(id);
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public User getByUsername(String uname) {
		try {
			return this.repo.getByUsername(uname);
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public User insertUser(User user) {
		user.setPassword(PasswordEncryptor.hash(user.getPassword()));
		try {
			return repo.save(user);
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public User updateUser(User user) {
		try {
			if (repo.getById(user.getId()) != null) {
				user.setPassword(PasswordEncryptor.hash(user.getPassword()));
				return repo.merge(user);
			} else {
				return null;
			}
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public void deleteUser(User user) {
		try {
			repo.deleteUser(user);
		} catch (RepositoryException e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean login(String username, String password) {
		User u = this.getByUsername(username);
		// if (u.getPassword().equals(PasswordEncryptor.hash(password))) {
		if (u.getPassword().equals(password)) {
			return true;
		}
		return false;
	}

}
