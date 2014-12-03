package repository;

import javax.ejb.Local;

import model.User;

@Local
public interface UserRepository extends BaseRepository<User, Long> {

	public User getByUsername(String username) throws RepositoryException;

	public void deleteUser(User username) throws RepositoryException;
}
