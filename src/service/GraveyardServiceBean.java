package service;

import java.util.List;

import javax.ejb.DependsOn;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import model.Graveyard;
import repository.GraveyardRepository;
import repository.RepositoryException;

@Stateless(name = "GraveyardService", mappedName = "GraveyardService")
@DependsOn({ "GraveyardRepository" })
@Local
public class GraveyardServiceBean implements GraveyardService {

	@EJB(name = "GraveyardRepository")
	GraveyardRepository repo;

	@Override
	public Graveyard insertGraveyard(Graveyard graveyard) {
		try {
			return repo.save(graveyard);
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
		return graveyard;
	}

	@Override
	public Graveyard updateGraveyard(Graveyard graveyard) {
		try {
			return this.repo.merge(graveyard);
		} catch (RepositoryException e) {
			return null;
		}
	}

	@Override
	public void deleteUser(Graveyard graveyard) {
		try {
			this.repo.delete(graveyard);
		} catch (RepositoryException e) {

		}
	}

	@Override
	public List<Graveyard> getAll() {
		try {
			return repo.getAll();
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
		return null;

	}

}
