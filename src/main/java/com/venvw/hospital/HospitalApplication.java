package com.venvw.hospital;

import com.venvw.hospital.dao.DiagnosisDao;
import com.venvw.hospital.dao.PeopleDao;
import com.venvw.hospital.dao.WardDao;
import com.venvw.hospital.model.*;
import com.venvw.hospital.utils.DatabaseEraseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;
import java.util.List;

@SpringBootApplication
public class HospitalApplication implements CommandLineRunner {
    private final DataSource dataSource;
    private final WardDao wardDao;
    private final DiagnosisDao diagnosisDao;
    private final PeopleDao peopleDao;

    @Autowired
    public HospitalApplication(DataSource dataSource, WardDao wardDao, DiagnosisDao diagnosisDao, PeopleDao peopleDao) {
        this.dataSource = dataSource;
        this.wardDao = wardDao;
        this.diagnosisDao = diagnosisDao;
        this.peopleDao = peopleDao;
    }

    public static void main(String[] args) {
        SpringApplication.run(HospitalApplication.class, args);
    }

    @Override
    public void run(String... args) {
        DatabaseEraseUtils.Erase(dataSource);

        // Запросы
        // 01. Создание палаты
        {
            System.out.println("------------01------------");

            Ward ward = new Ward("Sad Ward", 1);
            wardDao.persist(ward);

            System.out.println(ward.getId());
        }

        //02. Чтение палаты по ID
        {
            System.out.println("------------02------------");

            Ward ward = wardDao.find(1);

            System.out.println(ward);
        }

        //03. Чтение нескольких палат
        {
            System.out.println("------------03------------");

            wardDao.persist(new Ward("Another Ward", 1));
            wardDao.persist(new Ward("Yet another Ward", 1));
            List<Ward> wards = wardDao.findAll();

            System.out.println(wards);
        }

        //04. Обновление палаты
        {
            System.out.println("------------04------------");

            Ward ward = wardDao.find(1);

            System.out.println(ward);

            ward.setName("Merry Ward");
            ward.setMaxCount(5);
            wardDao.update(ward);

            ward = wardDao.find(1);

            System.out.println(ward);
        }

        //05. Удаление палаты
        {
            System.out.println("------------05------------");

            Ward ward = wardDao.find(1);

            System.out.println(ward);

            wardDao.remove(1);

            ward = wardDao.find(1);

            System.out.println(ward);
        }
        //06. Создание диагноза
        {
            System.out.println("------------06------------");

            Diagnosis diagnosis = new Diagnosis("Cholera");
            diagnosisDao.persist(diagnosis);

            System.out.println(diagnosis.getId());
        }
        //07. Получение диагноза по ID
        {
            System.out.println("------------07------------");

            Diagnosis diagnosis = diagnosisDao.find(1);

            System.out.println(diagnosis);
        }
        //08. Получение нескольких диагнозов
        {
            System.out.println("------------08------------");

            diagnosisDao.persist(new Diagnosis("Anthrax"));
            diagnosisDao.persist(new Diagnosis("Botulism"));
            diagnosisDao.persist(new Diagnosis("Sexually transmitted infections"));
            diagnosisDao.persist(new Diagnosis("Tuberculosis"));
            List<Diagnosis> diagnoses = diagnosisDao.findAll();

            System.out.println(diagnoses);
        }
        //09. Обновление диагноза
        {
            System.out.println("------------09------------");

            Diagnosis diagnosis = diagnosisDao.find(1);
            System.out.println(diagnosis);

            diagnosis.setName("Old cholera");
            diagnosisDao.update(diagnosis);

            diagnosis = diagnosisDao.find(1);

            System.out.println(diagnosis);
        }
        //10. Удаление диагноза
        {
            System.out.println("------------10------------");

            Diagnosis diagnosis = diagnosisDao.find(1);

            System.out.println(diagnosis);

            diagnosisDao.remove(1);
            diagnosis = diagnosisDao.find(1);

            System.out.println(diagnosis);
        }
        //11. Создание пациента
        {
            System.out.println("------------11------------");

            People people = new People("Vlasov", "Makar", "Michailovich", 2, 2);
            peopleDao.persist(people);

            System.out.println(people.getId());
        }
        //12. Получение пациента по ID
        {
            System.out.println("------------12------------");

            People people = peopleDao.find(1);

            System.out.println(people);
        }
        //13. Получение нескольких пациентов
        {
            System.out.println("------------13------------");

            peopleDao.persist(new People("Kirillov", "Miron", "Danilovich", 3, 2));
            peopleDao.persist(new People("Popov", "Georgy", "Artemovich", 3, 3));
            peopleDao.persist(new People("Uspenskaya", "Christina", "Andreeva", 4, 3));
            peopleDao.persist(new People("Efimov", "Simen", "Michailovich", 4, 3));
            List<People> people = peopleDao.findAll();

            System.out.println(people);
        }
        //14. Обновление данных о пациенте
        {
            System.out.println("------------14------------");

            People people = peopleDao.find(1);

            System.out.println(people);

            people.setFirstName("Dmitry");
            people.setLastName("Juravlev");
            people.setFatherName("Lukinichna");
            people.setDiagnosisId(4);
            people.setWardId(3);

            peopleDao.update(people);

            people = peopleDao.find(1);

            System.out.println(people);
        }
        //15. Удаление пациента
        {
            System.out.println("------------15------------");

            People people = peopleDao.find(1);

            System.out.println(people);

            peopleDao.remove(1);

            people = peopleDao.find(1);

            System.out.println(people);
        }
        //16. Чтение данных пациента и данных его болезни и палаты
        {
            System.out.println("------------16------------");

            PeopleDiagnosisWard peopleDiagnosisWard = peopleDao.findDiagnosisWard(2);

            System.out.println(peopleDiagnosisWard);
        }
        //17. Чтение данных пациентОВ и данных ИХ болезнЕЙ и палат
        {
            System.out.println("------------17------------");

            List<PeopleDiagnosisWard> peopleDiagnosisWards = peopleDao.findAllDiagnosisWard();

            System.out.println(peopleDiagnosisWards);
        }
        //18. Чтение палаты по ID и данных пациентов в ней
        {
            System.out.println("------------18------------");

            WardPeople wardPeople = wardDao.findWardPeople(3);

            System.out.println(wardPeople);
        }
        //19. Чтение палат и данных пациентов в НИХ
        {
            System.out.println("------------19------------");

            List<WardPeople> wardPeople = wardDao.findAllWardPeople();

            System.out.println(wardPeople);
        }
        //20. Чтение диагноза по ID и данных пациентов с ним
        {
            System.out.println("------------20------------");

            DiagnosisPeople diagnosisPeople = diagnosisDao.findPeople(4);

            System.out.println(diagnosisPeople);
        }
    }
}
