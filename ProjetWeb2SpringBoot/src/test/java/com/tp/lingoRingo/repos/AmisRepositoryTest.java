package com.tp.lingoRingo.repos;
import com.tp.lingoRingo.repos.AmisRepository;
import com.tp.lingoRingo.repos.UtilisateursRepository;
import com.tp.lingoRingo.entities.Ami;
import com.tp.lingoRingo.entities.Utilisateur;
import com.tp.lingoRingo.repos.UtilisateursRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)  // Empêcher la suppression de données créées

class AmisRepositoryTest {
    @Autowired
    AmisRepository amiRepos;
    @Autowired
    private TestEntityManager entityManager;  // Pour gérer les testes

    @Test
    public void findAmiByUtilisateurAdmin() {
        int id_utilisateur1 = 1;
        Iterable<Ami> amis = amiRepos.findAmiByUtilisateurAdmin(id_utilisateur1);

        for (Ami ami : amis) {
            System.out.println(ami.toString());
        }
    }




    @Test
    public void testCreerDemandAmi(){
        int id_utilisateur1 = 2;
        int id_utilisateur2 = 12;
        amiRepos.creerDemandeAmi(id_utilisateur1, id_utilisateur2);
    }

    @Test
    public void testAccepterDemandeAmi(){

        int id_utilisateur1 = 2;
        int id_utilisateur2 = 3;
        // Acceptation de la demande d'ami
        amiRepos.accepterDemandeAmi(id_utilisateur1, id_utilisateur2);

    }

    @Test
    public void testRefuserDemandeAmi(){
        int id_utilisateur1 = 2;
        int id_utilisateur2 = 4;
        amiRepos.refuserDemandeAmi(id_utilisateur1,id_utilisateur2);
    }



    @Test
    public void testSupprimerRelationAmi() {
        int id_utilisateur1 = 2;
        int id_utilisateur2 = 5;

        amiRepos.supprimerRelationAmi(id_utilisateur1, id_utilisateur2);
    }



    @Test
    public void testAjouterFavoris(){
        int id_utilisateur1 = 1;
        int id_utilisateur2 = 3;
        amiRepos.ajouterFavori(id_utilisateur1,id_utilisateur2);

    }

    @Test
    public void testRetirerFavoris(){
        int id_utilisateur1 = 1;
        int id_utilisateur2 = 3;
        amiRepos.retirerFavori(id_utilisateur1,id_utilisateur2);

    }


}
