package repositoryBeans;

import javax.ejb.Stateless;

import model.Graveyard;
import repository.GraveyardRepository;
import repository.RepositoryException;

@Stateless(name = "GraveyardRepository", mappedName = "GraveyardRepository")
public class GraveyardRepositoryBean extends
		BaseRepositoryBean<Graveyard, Long> implements GraveyardRepository{

	public GraveyardRepositoryBean() {
		super(Graveyard.class);
	}

	@Override
	public Graveyard getById(Long id) throws RepositoryException {
		return this.getEntityManager().find(Graveyard.class, id);
	}

}
