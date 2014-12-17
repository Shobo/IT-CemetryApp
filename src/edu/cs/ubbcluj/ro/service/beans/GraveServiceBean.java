package edu.cs.ubbcluj.ro.service.beans;

import java.util.List;

import javax.ejb.DependsOn;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import edu.cs.ubbcluj.ro.model.Grave;
import edu.cs.ubbcluj.ro.repository.GraveRepository;
import edu.cs.ubbcluj.ro.repository.RepositoryException;
import edu.cs.ubbcluj.ro.repository.service.GraveService;

@Stateless(name = "GraveService", mappedName = "GraveService")
@DependsOn({ "GraveRepository" })
@Local
public class GraveServiceBean implements GraveService {

	@EJB(name = "GraveRepository")
	GraveRepository repo;

	@Override
	public Grave insertGrave(Grave grave) {
		try {
			return repo.save(grave);
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
		return grave;
	}

	@Override
	public Grave updateGrave(Grave grave) {
		try {
			return this.repo.merge(grave);
		} catch (RepositoryException e) {
			return null;
		}
	}

	@Override
	public void deleteGrave(Grave grave) {
		try {
			this.repo.delete(grave);
		} catch (RepositoryException e) {

		}
		
	}

	@Override
	public List<Grave> getAll() {
		try {
			return repo.getAll();
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
		return null;

	}

	
}
