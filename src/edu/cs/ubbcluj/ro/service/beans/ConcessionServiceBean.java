package edu.cs.ubbcluj.ro.service.beans;

import java.util.List;

import javax.ejb.DependsOn;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import edu.cs.ubbcluj.ro.model.Concession;
import edu.cs.ubbcluj.ro.repository.ConcessionRepository;
import edu.cs.ubbcluj.ro.repository.RepositoryException;
import edu.cs.ubbcluj.ro.repository.service.ConcessionService;

@Stateless(name = "ConcessionService", mappedName = "ConcessionService")
@DependsOn({ "ConcessionRepository" })
@Local
public class ConcessionServiceBean implements ConcessionService {

	@EJB(name = "ConcessionRepository")
	ConcessionRepository repo;

	@Override
	public Concession insertConcession(Concession Concession) {
		try {
			return repo.save(Concession);
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
		return Concession;
	}

	@Override
	public Concession updateConcession(Concession Concession) {
		try {
			return this.repo.merge(Concession);
		} catch (RepositoryException e) {
			return null;
		}
	}

	@Override
	public void deleteConcession(Concession Concession) {
		try {
			this.repo.delete(Concession);
		} catch (RepositoryException e) {

		}
	}

	@Override
	public List<Concession> getAll() {
		try {
			return repo.getAll();
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
		return null;
	}

}
