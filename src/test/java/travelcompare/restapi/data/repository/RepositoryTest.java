package travelcompare.restapi.data.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import travelcompare.restapi.SpringTest;

@Transactional
public abstract class RepositoryTest extends SpringTest {

    @Autowired
    protected UserRepository userRepository;

}
