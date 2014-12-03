package repository;

import javax.ejb.Local;

import model.Graveyard;


@Local
public interface GraveyardRepository extends BaseRepository<Graveyard, Long>{

}
