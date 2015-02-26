package net.sf.anathema.hero.merits.persistence;

import java.util.ArrayList;
import java.util.List;

import net.sf.anathema.library.persister.KnownOptionalTraitPto;
import net.sf.anathema.library.persister.OptionalTraitsModelPto;

public class MeritsPto implements OptionalTraitsModelPto {

  public List<KnownOptionalTraitPto> merits = new ArrayList<>();

	@Override
	public List<KnownOptionalTraitPto> getOptionalTraitPtoList() {
		return merits;
	}
}
