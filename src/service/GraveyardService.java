package service;

import java.util.List;

import javax.ejb.Local;

import model.Graveyard;

@Local
public interface GraveyardService {
	Graveyard insertGraveyard(Graveyard graveyard);

	Graveyard updateGraveyard(Graveyard graveyard);

	void deleteUser(Graveyard graveyard);

	List<Graveyard> getAll();
}
