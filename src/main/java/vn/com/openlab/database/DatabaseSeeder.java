package vn.com.openlab.database;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import vn.com.openlab.model.Category;
import vn.com.openlab.model.Role;
import vn.com.openlab.model.User;
import vn.com.openlab.repository.CategoryRepository;
import vn.com.openlab.repository.RoleRepository;
import vn.com.openlab.repository.UserRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Configuration
public class DatabaseSeeder {

    private final PasswordEncoder passwordEncoder;

    public DatabaseSeeder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public CommandLineRunner dataLoader(RoleRepository roleRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        return args -> {
            if (roleRepository.count() == 0) {
                // Tạo vai trò ADMIN và USER
                Role adminRole = roleRepository.save(new Role("ADMIN"));
                Role userRole = roleRepository.save(new Role("USER"));

                // Tạo người dùng mẫu
                if (userRepository.count() == 0) {
                    // Convert LocalDate to Date
                    LocalDate localDate = LocalDate.of(2002, 9, 26); // Set a valid date
                    Date dateOfBirth = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

                    // Tạo người dùng với vai trò ADMIN
                    User adminUser = User.builder()
                            .fullName("Admin User")
                            .phoneNumber("123456789")
                            .password(passwordEncoder.encode("1234"))
                            .active(true)
                            .role(adminRole) // Gán vai trò ADMIN cho người dùng
                            .address("Van Quan, Ha Dong, Ha Noi")
                            .dateOfBirth(dateOfBirth)
                            .build();
                    userRepository.save(adminUser);

                    // Tạo người dùng với vai trò USER
                    User normalUser = User.builder()
                            .fullName("Normal User")
                            .phoneNumber("987654321")
                            .password(passwordEncoder.encode("1234"))
                            .active(true)
                            .role(userRole) // Gán vai trò USER cho người dùng
                            .address("My Dinh, Ha Noi")
                            .dateOfBirth(dateOfBirth)
                            .build();
                    userRepository.save(normalUser);
                }
            }

            // Tạo danh mục nếu chưa tồn tại
            if (categoryRepository.count() == 0) {
                categoryRepository.save(new Category("Electronics"));
                categoryRepository.save(new Category("Books"));
                categoryRepository.save(new Category("Clothing"));
                categoryRepository.save(new Category("Home & Kitchen"));
                categoryRepository.save(new Category("Sports & Outdoors"));
                // Thêm nhiều danh mục khác nếu cần
            }
        };
    }
}
