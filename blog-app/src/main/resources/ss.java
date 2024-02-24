import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MyService {

    @Autowired
    private MyRepository myRepository;

    @Transactional(noRollbackFor = UserNotExistException.class)
    public void outerTransaction() {
        // Perform some operations within the outer transaction
        myRepository.saveData("Data for outer transaction");

        // Call the method with NESTED propagation
        innerTransaction();
    }

    
    public void innerTransaction() {
        // Perform some operations within the nested transaction
        myRepository.saveData("Data for inner transaction");

        // Simulate an error that will cause the nested transaction to be rolled back
        try{
            throw new UserNotExistException("Simulated error in inner transaction");
        }catch(UserNotExistException ex){
            ex.printStackTrace();
        }

    }
}