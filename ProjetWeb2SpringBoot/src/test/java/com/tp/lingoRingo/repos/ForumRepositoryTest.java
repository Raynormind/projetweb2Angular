package com.tp.lingoRingo.repos;

import com.tp.lingoRingo.entities.Forum;
import com.tp.lingoRingo.repos.ForumRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest  // permet de configurer l'environnement de test en chargement les éléments necessaires
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

@Rollback(false)
public class ForumRepositoryTest {
    @Autowired
    ForumRepository repos;
    @Autowired
    private TestEntityManager entityManager;


    //(int id_forum, String titre_forum, String langue, String interets_culturels, boolean etat_forum)
    @Test
    public void testSaveForum(){
        Forum forumAnime = new Forum("youssef","anglais", "sport", true);

        Forum savedForum =  repos.save(forumAnime);
        assertThat(savedForum.getId_forum()).isGreaterThan(0);
    }


    @Test
    public void testFindAllForums() {
        List<Forum> listForums = repos.findAll();
        assertThat(listForums).isNotNull();
        assertThat(listForums.size()).isGreaterThan(0);
        listForums.forEach(forum -> System.out.println(forum));
    }

    @Test
    public void testFindByLangue() {

        List<Forum> listForumsLangue = repos.findForumsByLangue("anglais");
        assertThat(listForumsLangue).isNotNull();
        assertThat(listForumsLangue).allMatch(forum -> forum.getLangue().equals("anglais"));
        listForumsLangue.forEach(forum -> System.out.println(forum));
    }

    @Test
    public void testFindByTitre() {

        List<Forum> forums = repos.findForumsByTitre("youssef");
        assertThat(forums).isNotNull();
        assertThat(forums).anyMatch(forum -> forum.getTitre_forum().contains("youssef"));
        forums.forEach(forum -> System.out.println(forum));
    }

    @Test
    public void testFindByInteretsCulturels() {

        List<Forum> forums = repos.findForumsByInteretsCulturels("sport");
        assertThat(forums).isNotNull();
        assertThat(forums).anyMatch(forum -> forum.getInterets_culturels().equals("sport"));
        forums.forEach(forum -> System.out.println(forum));
    }
}