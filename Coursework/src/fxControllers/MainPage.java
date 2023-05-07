package fxControllers;

import hibernateControllers.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;
import utils.DbUtils;

import javax.persistence.EntityManagerFactory;
import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainPage implements Initializable {
    @FXML
    public TextField makeField;
    @FXML
    public TextField odometerField;
    @FXML
    public TextField modelField;
    @FXML
    public TextField tankCapacityField;
    @FXML
    public TextField yearField;
    @FXML
    public ComboBox<TyreType> tyreTypeField;
    @FXML
    public ListView<Truck> truckListField;
    @FXML
    public Label selectedTruckTyre;
    @FXML
    public ListView<Driver> driverListField;
    @FXML
    public ListView<Manager> managerListField;
    @FXML
    public TextField loginField;
    @FXML
    public TextField nameField;
    @FXML
    public TextField surnameField;
    @FXML
    public PasswordField pswField;
    @FXML
    public PasswordField repPswField;
    @FXML
    public DatePicker bDateField;
    @FXML
    public TextField phoneNumField;
    @FXML
    public TextField managerEmailField;
    @FXML
    public RadioButton radioD;
    @FXML
    public RadioButton radioM;
    @FXML
    public DatePicker medCertField;
    @FXML
    public TextField medCertNum;
    @FXML
    public TextField driverLicenseField;
    @FXML
    public ToggleGroup userType;
    @FXML
    public Tab userManagementTab;
    @FXML
    public ListView<Cargo> cargoListField;
    @FXML
    public TextField titleField;
    @FXML
    public TextField weightField;
    @FXML
    public TextField customerField;
    @FXML
    public ComboBox<CargoType> cargoTypeField;
    @FXML
    public TextField descriptionField;
    @FXML
    public Label cargoDateCreated;
    @FXML
    public Label cargoDateUpdated;
    @FXML
    public Label cargoSelectedType;
    @FXML
    public ListView<Destination> destinationListField;
    @FXML
    public ListView<Checkpoint> unAssignedCheckpointList;
    @FXML
    public ListView<Manager> assignedManagers;
    @FXML
    public ListView<Manager> unAssignedManagers;
    @FXML
    public ListView<Checkpoint> assignedCheckpointList;
    @FXML
    public TextField startLnField;
    @FXML
    public TextField endLnField;
    @FXML
    public TextField startLatField;
    @FXML
    public TextField endLatField;
    @FXML
    public TextField startCityField;
    @FXML
    public TextField endCityField;
    @FXML
    public ListView<Truck> truckListDestField;
    @FXML
    public ListView<Checkpoint> checkpointListField;
    @FXML
    public TextField titleCheckpointField;
    @FXML
    public CheckBox longStopCheck;
    @FXML
    public Label selectedDestTruck;
    @FXML
    public ListView<Cargo> cargoListDestField;
    @FXML
    public Label selectedDestCargo;
    @FXML
    public ListView<User> userListField;
    @FXML
    public Button createUserButton;
    @FXML
    public Button updateUserButton;
    @FXML
    public Button deleteUserButton;
    @FXML
    public Button updateCargoButton;
    @FXML
    public Button deleteCargoButton;
    @FXML
    public Button addCargoButton;
    @FXML
    public Button createTruckButton;
    @FXML
    public Button updateTruckButton;
    @FXML
    public Button deleteTruckButton;
    @FXML
    public Button assignManagerButton;
    @FXML
    public Button unAssignManagerButton;
    @FXML
    public Button assignCheckpointButton;
    @FXML
    public Button unAssignCheckpointButton;
    @FXML
    public Button addDestinationButton;
    @FXML
    public Button deleteDestinationButton;
    @FXML
    public Button updateDestinationButton;
    @FXML
    public Button createCheckpointButton;
    @FXML
    public Button updateCheckpointButton;
    @FXML
    public Button deleteCheckpointButton;

    private User loggedUser;
    List<TyreType> tyre = List.of(TyreType.values());
    private EntityManagerFactory entityManagerFactory;
    private HibernateController hibernateController = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userManagementTab.setDisable(true);    //should be true when admin check implemented
        radioM.setSelected(true);
        disableFields();
        tyreTypeField.getItems().addAll(TyreType.values());
        cargoTypeField.getItems().addAll(CargoType.values());

        truckListField.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                int i = truckListField.getSelectionModel().getSelectedIndex();

                makeField.setText(truckListField.getItems().get(i).getMake());
                modelField.setText(truckListField.getItems().get(i).getModel());
                yearField.setText(String.valueOf(truckListField.getItems().get(i).getYear()));
                odometerField.setText(String.valueOf(truckListField.getItems().get(i).getOdometer()));
                tankCapacityField.setText(String.valueOf(truckListField.getItems().get(i).getFuelTankCapacity()));
                selectedTruckTyre.setVisible(true);
                selectedTruckTyre.setText(String.valueOf(truckListField.getItems().get(i).getTyreType()));
            }
        });
        driverListField.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Driver>() {
            @Override
            public void changed(ObservableValue<? extends Driver> observable, Driver oldValue, Driver newValue) {
                int i = driverListField.getSelectionModel().getSelectedIndex();

                radioD.setSelected(true);
                radioM.setSelected(false);
                disableFields();
                loginField.setText(driverListField.getItems().get(i).getLogin());
                nameField.setText(driverListField.getItems().get(i).getName());
                surnameField.setText(driverListField.getItems().get(i).getSurname());
                pswField.setText(driverListField.getItems().get(i).getPassword());
                repPswField.setText(driverListField.getItems().get(i).getPassword());
                bDateField.setValue(driverListField.getItems().get(i).getBirthDate());
                phoneNumField.setText(driverListField.getItems().get(i).getPhoneNum());
                medCertField.setValue(driverListField.getItems().get(i).getMedCertificateDate());
                medCertNum.setText(driverListField.getItems().get(i).getMedCertificateNumber());
                driverLicenseField.setText(driverListField.getItems().get(i).getDriverLicense());
            }
        });
        managerListField.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Manager>() {
            @Override
            public void changed(ObservableValue<? extends Manager> observable, Manager oldValue, Manager newValue) {
                int i = managerListField.getSelectionModel().getSelectedIndex();

                radioM.setSelected(true);
                radioD.setSelected(false);
                disableFields();
                loginField.setText(managerListField.getItems().get(i).getLogin());
                nameField.setText(managerListField.getItems().get(i).getName());
                surnameField.setText(managerListField.getItems().get(i).getSurname());
                pswField.setText(managerListField.getItems().get(i).getPassword());
                repPswField.setText(managerListField.getItems().get(i).getPassword());
                bDateField.setValue(managerListField.getItems().get(i).getBirthDate());
                phoneNumField.setText(managerListField.getItems().get(i).getPhoneNum());
                managerEmailField.setText(managerListField.getItems().get(i).getEmail());
            }
        });
        cargoListField.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Cargo>() {
            @Override
            public void changed(ObservableValue<? extends Cargo> observable, Cargo oldValue, Cargo newValue) {
                int i = cargoListField.getSelectionModel().getSelectedIndex();

                cargoDateCreated.setVisible(true);
                cargoDateCreated.setText(String.valueOf(cargoListField.getItems().get(i).getDateCreated()));
                if (cargoListField.getItems().get(i).getDateUpdated() == null) {
                    cargoDateUpdated.setVisible(false);
                } else {
                    cargoDateUpdated.setText(String.valueOf(cargoListField.getItems().get(i).getDateUpdated()));
                    cargoDateUpdated.setVisible(true);
                }
                cargoSelectedType.setText(String.valueOf(cargoListField.getItems().get(i).getCargoType()));
                cargoSelectedType.setVisible(true);
                descriptionField.setText(cargoListField.getItems().get(i).getDescription());
                titleField.setText(cargoListField.getItems().get(i).getTitle());
                weightField.setText(String.valueOf(cargoListField.getItems().get(i).getWeight()));
                customerField.setText(cargoListField.getItems().get(i).getCustomer());
            }
        });
        destinationListField.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Destination>() {
            @Override
            public void changed(ObservableValue<? extends Destination> observable, Destination oldValue, Destination newValue) {

                loadDestinationsSelected();
                int i = destinationListField.getSelectionModel().getSelectedIndex();

                startLnField.setText(String.valueOf(destinationListField.getItems().get(i).getStartLn()));
                startLatField.setText(String.valueOf(destinationListField.getItems().get(i).getStartLat()));
                endLnField.setText(String.valueOf(destinationListField.getItems().get(i).getEndLn()));
                endLatField.setText(String.valueOf(destinationListField.getItems().get(i).getEndLat()));
                startCityField.setText(String.valueOf(destinationListField.getItems().get(i).getStartCity()));
                endCityField.setText(String.valueOf(destinationListField.getItems().get(i).getEndCity()));

                selectedDestTruck.setText(String.valueOf(destinationListField.getItems().get(i).getTruck()));
                selectedDestTruck.setVisible(true);
                selectedDestCargo.setText(String.valueOf(destinationListField.getItems().get(i).getCargo()));
                selectedDestCargo.setVisible(true);
                /*
                int i = destinationListField.getSelectionModel().getSelectedIndex();


                truckListDestField.getItems().clear();
                truckListDestField.getItems().clear();
                unAssignedManagers.getItems().clear();
                unAssignedCheckpointList.getItems().clear();

                truckListDestField.getItems().addAll(truckListField.getItems());
                unAssignedManagers.getItems().addAll(managerListField.getItems());
                unAssignedCheckpointList.getItems().addAll(checkpointListField.getItems());

                assignedManagers.getItems().clear();
                assignedCheckpointList.getItems().clear();

                if (destinationListField.getItems().get(i).getCheckpointList() != null) {
                    assignedCheckpointList.getItems().addAll(destinationListField.getItems().get(i).getCheckpointList());
                    unAssignedCheckpointList.getItems().removeAll(assignedCheckpointList.getItems());
                }
                if (destinationListField.getItems().get(i).getResponsibleManagers() != null) {
                    assignedManagers.getItems().addAll(destinationListField.getItems().get(i).getResponsibleManagers());
                    unAssignedManagers.getItems().removeAll(assignedManagers.getItems());
                }
                 */
            }
        });
        checkpointListField.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Checkpoint>() {
            @Override
            public void changed(ObservableValue<? extends Checkpoint> observable, Checkpoint oldValue, Checkpoint newValue) {
                int i = checkpointListField.getSelectionModel().getSelectedIndex();

                titleCheckpointField.setText(checkpointListField.getItems().get(i).getTitle());
                longStopCheck.setSelected(checkpointListField.getItems().get(i).isLongStop());
            }
        });
        userListField.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>() {
            @Override
            public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) {
                User user = hibernateController.getEntityById(User.class, userListField.getItems().get(userListField.getSelectionModel().getSelectedIndex()).getId());

                loginField.setText(user.getLogin());
                nameField.setText(user.getName());
                surnameField.setText(user.getSurname());
                pswField.setText(user.getPassword());
                repPswField.setText(user.getPassword());
                bDateField.setValue(user.getBirthDate());
                phoneNumField.setText(user.getPhoneNum());

                if(hibernateController.getEntityById(User.class, userListField.getItems().get(userListField.getSelectionModel().getSelectedIndex()).getId()).getClass()==Manager.class){
                    Manager manager = (Manager) hibernateController.getEntityById(User.class, userListField.getItems().get(userListField.getSelectionModel().getSelectedIndex()).getId());

                    radioM.setSelected(true);
                    radioD.setSelected(false);
                    disableFields();

                    managerEmailField.setText(manager.getEmail());
                }
                else{
                    Driver driver = (Driver) hibernateController.getEntityById(User.class, userListField.getItems().get(userListField.getSelectionModel().getSelectedIndex()).getId());

                    radioM.setSelected(false);
                    radioD.setSelected(true);
                    disableFields();

                    medCertField.setValue(driver.getMedCertificateDate());
                    medCertNum.setText(driver.getMedCertificateNumber());
                    driverLicenseField.setText(driver.getDriverLicense());
                }
            }
        });
    }

    public void setInfo(User user) throws SQLException, ClassNotFoundException {
        List<User> userList = new ArrayList<>(hibernateController.getAllRecords(User.class));
        for (User value : userList) {
            if (Objects.equals(value.getLogin(), user.getLogin())) {
                this.loggedUser = value;
                break;
            }
        }
        //hibernateController.getEntityById(User.class, user.getId());
        /*
        if (DbUtils.isAdmin(user)) {
            userManagementTab.setDisable(false);
        }
        */
    }

    public void setData(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.hibernateController = new HibernateController(entityManagerFactory);
    }

    public void createTruck(javafx.event.ActionEvent actionEvent) {
        //System.out.println(loggedUser);
        try {
            Truck truck = new Truck(makeField.getText(), modelField.getText(), Integer.parseInt(yearField.getText()), Double.parseDouble(odometerField.getText()), Double.parseDouble(tankCapacityField.getText()), tyre.get(tyreTypeField.getSelectionModel().getSelectedIndex()));
            hibernateController.create(truck);
            loadTrucks();
        } catch (ArrayIndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(null, "You need to choose tyre type", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error has occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteTruck(ActionEvent actionEvent) {
        try {
            //hibernateController.delete(truckListField.getItems().get(truckListField.getSelectionModel().getSelectedIndex()));
            hibernateController.deleteById(Truck.class, truckListField.getItems().get(truckListField.getSelectionModel().getSelectedIndex()).getId());
            loadTrucks();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error has occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateTruck(ActionEvent actionEvent) {
        try {
            int i = truckListField.getSelectionModel().getSelectedIndex();
            truckListField.getItems().get(i).setMake(makeField.getText());
            truckListField.getItems().get(i).setModel(modelField.getText());
            truckListField.getItems().get(i).setYear(Integer.parseInt(yearField.getText()));
            truckListField.getItems().get(i).setOdometer(Double.parseDouble(odometerField.getText()));
            truckListField.getItems().get(i).setFuelTankCapacity(Double.parseDouble(tankCapacityField.getText()));
            truckListField.getItems().get(i).setTyreType(tyre.get(tyreTypeField.getSelectionModel().getSelectedIndex()));

            hibernateController.update(truckListField.getItems().get(i));
            loadTrucks();
        } catch (ArrayIndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(null, "You need to choose tyre type", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error has occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void disableFields() {
        if (radioD.isSelected()) {
            medCertField.setDisable(false);
            medCertNum.setDisable(false);
            driverLicenseField.setDisable(false);
            managerEmailField.setDisable(true);
        } else {
            medCertField.setDisable(true);
            medCertNum.setDisable(true);
            driverLicenseField.setDisable(true);
            managerEmailField.setDisable(false);
        }
    }

    public void createNewUser(ActionEvent actionEvent) {
        if (Objects.equals(pswField.getText(), repPswField.getText())) {
            if (radioD.isSelected()) {
                Driver driver = new Driver(loginField.getText(), pswField.getText(), nameField.getText(), surnameField.getText(), LocalDate.parse(bDateField.getValue().toString()), phoneNumField.getText(), LocalDate.parse(medCertField.getValue().toString()), medCertNum.getText(), driverLicenseField.getText());
                hibernateController.create(driver);
                driverListField.getItems().add(driver);
            } else {
                Manager manager = new Manager(loginField.getText(), pswField.getText(), nameField.getText(), surnameField.getText(), LocalDate.parse(bDateField.getValue().toString()), phoneNumField.getText(), managerEmailField.getText());
                hibernateController.create(manager);
                managerListField.getItems().add(manager);
            }
            loadUsers();
        } else {
            JOptionPane.showMessageDialog(null, "Password and password repeat fields don't match", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void goToForum(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../view/forum-page.fxml")); //getting things
        Parent parent = fxmlLoader.load(); //loading things

        ForumPage forumPage = fxmlLoader.getController();
        forumPage.setInfo(loggedUser);

        Scene scene = new Scene(parent);
        Stage stage = (Stage) loginField.getScene().getWindow();
        stage.setTitle("Forum");
        stage.setScene(scene);
        stage.show();
    }

    public void updateUser(ActionEvent actionEvent) {
        if (Objects.equals(pswField.getText(), repPswField.getText())) {
            if(driverListField.isVisible()&&managerListField.isVisible()) {
                if (radioD.isSelected()) {
                    int i = driverListField.getSelectionModel().getSelectedIndex();
                    driverListField.getItems().get(i).setLogin(loginField.getText());
                    driverListField.getItems().get(i).setName(nameField.getText());
                    driverListField.getItems().get(i).setSurname(surnameField.getText());
                    driverListField.getItems().get(i).setPassword(pswField.getText());
                    driverListField.getItems().get(i).setBirthDate(bDateField.getValue());
                    driverListField.getItems().get(i).setPhoneNum(phoneNumField.getText());
                    driverListField.getItems().get(i).setMedCertificateDate(medCertField.getValue());
                    driverListField.getItems().get(i).setMedCertificateNumber(medCertNum.getText());
                    driverListField.getItems().get(i).setDriverLicense(driverLicenseField.getText());
                    hibernateController.update(driverListField.getItems().get(i));
                } else {
                    int i = managerListField.getSelectionModel().getSelectedIndex();
                    managerListField.getItems().get(i).setLogin(loginField.getText());
                    managerListField.getItems().get(i).setName(nameField.getText());
                    managerListField.getItems().get(i).setSurname(surnameField.getText());
                    managerListField.getItems().get(i).setPassword(pswField.getText());
                    managerListField.getItems().get(i).setBirthDate(bDateField.getValue());
                    managerListField.getItems().get(i).setPhoneNum(phoneNumField.getText());
                    managerListField.getItems().get(i).setEmail(managerEmailField.getText());
                    hibernateController.update(managerListField.getItems().get(i));
                }
            }
            else {
                if (radioD.isSelected()) {
                    Driver d = (Driver) hibernateController.getEntityById(User.class, userListField.getItems().get(userListField.getSelectionModel().getSelectedIndex()).getId());
                    d.setLogin(loginField.getText());
                    d.setName(nameField.getText());
                    d.setSurname(surnameField.getText());
                    d.setPassword(pswField.getText());
                    d.setBirthDate(bDateField.getValue());
                    d.setPhoneNum(phoneNumField.getText());
                    d.setMedCertificateDate(medCertField.getValue());
                    d.setMedCertificateNumber(medCertNum.getText());
                    d.setDriverLicense(driverLicenseField.getText());
                    hibernateController.update(d);
                } else {
                    Manager m = (Manager) hibernateController.getEntityById(Manager.class, userListField.getItems().get(userListField.getSelectionModel().getSelectedIndex()).getId());
                    m.setLogin(loginField.getText());
                    m.setName(nameField.getText());
                    m.setSurname(surnameField.getText());
                    m.setPassword(pswField.getText());
                    m.setBirthDate(bDateField.getValue());
                    m.setPhoneNum(phoneNumField.getText());
                    m.setEmail(managerEmailField.getText());
                    hibernateController.update(m);
                }
            }
            loadUsers();
        } else {
            JOptionPane.showMessageDialog(null, "Password and password repeat fields don't match", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteUser(ActionEvent actionEvent) {
        if (driverListField.isVisible()&&managerListField.isVisible()) {
            if (radioD.isSelected()) {
                driverListField.getItems().remove(driverListField.getSelectionModel().getSelectedIndex());
            } else {
                managerListField.getItems().remove(managerListField.getSelectionModel().getSelectedIndex());
            }
        }else{
            try {
                //hibernateController.delete(userListField.getItems().get(userListField.getSelectionModel().getSelectedIndex()));
                hibernateController.deleteById(User.class, userListField.getItems().get(userListField.getSelectionModel().getSelectedIndex()).getId());
                loadUsers();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void updateCargo(ActionEvent actionEvent) {
        if (cargoTypeField.getValue() != null) {
            try {
                int i = cargoListField.getSelectionModel().getSelectedIndex();

                cargoListField.getItems().get(i).setCargoType(cargoTypeField.getValue());
                cargoListField.getItems().get(i).setDateUpdated(LocalDate.now());
                cargoListField.getItems().get(i).setTitle(titleField.getText());
                cargoListField.getItems().get(i).setWeight(Double.parseDouble(weightField.getText()));
                cargoListField.getItems().get(i).setDescription(descriptionField.getText());
                cargoListField.getItems().get(i).setCustomer(customerField.getText());
                hibernateController.update(cargoListField.getItems().get(i));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "An error has occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            loadCargo();
        } else {
            JOptionPane.showMessageDialog(null, "You need to choose cargo type", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteCargo(ActionEvent actionEvent) {
        //hibernateController.delete(hibernateController.getEntityById(Cargo.class,cargoListField.getItems().get(cargoListField.getSelectionModel().getSelectedIndex()).getId()));
        //hibernateController.delete(cargoListField.getItems().get(cargoListField.getSelectionModel().getSelectedIndex()));
        hibernateController.deleteById(Cargo.class,cargoListField.getItems().get(cargoListField.getSelectionModel().getSelectedIndex()).getId());
        loadCargo();
    }

    public void addCargo(ActionEvent actionEvent) {
        if (cargoTypeField.getValue() != null) {
            try {
                Cargo cargo = new Cargo(titleField.getText(), Double.parseDouble(weightField.getText()), cargoTypeField.getValue(), descriptionField.getText(), customerField.getText());
                hibernateController.create(cargo);
                loadCargo();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "An error has occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "You need to choose cargo type", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateDestInfo(Event event) {

        loadDestinations();

        /*
        truckListDestField.getItems().clear();
        cargoListDestField.getItems().clear();
        unAssignedManagers.getItems().clear();
        unAssignedCheckpointList.getItems().clear();

        truckListDestField.getItems().addAll(truckListField.getItems());
        cargoListDestField.getItems().addAll(cargoListField.getItems());
        unAssignedManagers.getItems().addAll(managerListField.getItems());
        unAssignedCheckpointList.getItems().addAll(checkpointListField.getItems());

        assignedManagers.getItems().clear();
        assignedCheckpointList.getItems().clear();
        if (destinationListField.getSelectionModel().getSelectedIndex() != -1) {
            assignedManagers.getItems().addAll(destinationListField.getItems().get(destinationListField.getSelectionModel().getSelectedIndex()).getResponsibleManagers());
            unAssignedManagers.getItems().removeAll(assignedManagers.getItems());
            assignedCheckpointList.getItems().addAll(destinationListField.getItems().get(destinationListField.getSelectionModel().getSelectedIndex()).getCheckpointList());
            unAssignedCheckpointList.getItems().removeAll(assignedCheckpointList.getItems());
        }
         */
    }

    public void assignManager(ActionEvent actionEvent) {
        Destination destination = hibernateController.getEntityById(Destination.class, destinationListField.getItems().get(destinationListField.getSelectionModel().getSelectedIndex()).getId());
        Manager manager = hibernateController.getEntityById(Manager.class, unAssignedManagers.getItems().get(unAssignedManagers.getSelectionModel().getSelectedIndex()).getId());

        destination.getResponsibleManagers().add(manager);
        hibernateController.update(destination);

        manager.getMyManagedDestinations().add(destination);
        hibernateController.update(manager);

        loadDestinationsSelected();
    }

    public void unAssignManager(ActionEvent actionEvent) {
        Destination destination = hibernateController.getEntityById(Destination.class, destinationListField.getItems().get(destinationListField.getSelectionModel().getSelectedIndex()).getId());
        Manager manager = hibernateController.getEntityById(Manager.class, assignedManagers.getItems().get(assignedManagers.getSelectionModel().getSelectedIndex()).getId());

        destination.getResponsibleManagers().remove(manager);
        hibernateController.update(destination);

        manager.getMyManagedDestinations().remove(destination);
        hibernateController.update(manager);

        loadDestinationsSelected();
    }

    public void assignCheckpoint(ActionEvent actionEvent) {
        Destination destination = hibernateController.getEntityById(Destination.class, destinationListField.getItems().get(destinationListField.getSelectionModel().getSelectedIndex()).getId());
        Checkpoint checkpoint = hibernateController.getEntityById(Checkpoint.class, unAssignedCheckpointList.getItems().get(unAssignedCheckpointList.getSelectionModel().getSelectedIndex()).getId());

        destination.getCheckpointList().add(checkpoint);
        hibernateController.update(destination);

        checkpoint.setDestination(destination);
        hibernateController.update(checkpoint);

        loadDestinationsSelected();
    }

    public void unAssignCheckpoint(ActionEvent actionEvent) {
        Destination destination = hibernateController.getEntityById(Destination.class, destinationListField.getItems().get(destinationListField.getSelectionModel().getSelectedIndex()).getId());
        Checkpoint checkpoint = hibernateController.getEntityById(Checkpoint.class, assignedCheckpointList.getItems().get(assignedCheckpointList.getSelectionModel().getSelectedIndex()).getId());

        destination.getCheckpointList().remove(checkpoint);
        hibernateController.update(destination);

        checkpoint.setDestination(null);
        hibernateController.update(checkpoint);

        loadDestinationsSelected();
    }

    public void addDestination(ActionEvent actionEvent) {
        Destination destination = new Destination(startCityField.getText(), Long.parseLong(startLnField.getText()), Long.parseLong(startLatField.getText()), endCityField.getText(), Long.parseLong(endLnField.getText()), Long.parseLong(endLatField.getText()), truckListDestField.getItems().get(truckListDestField.getSelectionModel().getSelectedIndex()), cargoListDestField.getItems().get(cargoListDestField.getSelectionModel().getSelectedIndex()));
        hibernateController.create(destination);
        truckListDestField.getItems().get(truckListDestField.getSelectionModel().getSelectedIndex()).setCurrentDestination(hibernateController.getEntityById(Destination.class, destination.getId()));
        hibernateController.update(truckListDestField.getItems().get(truckListDestField.getSelectionModel().getSelectedIndex()));
        cargoListDestField.getItems().get(cargoListDestField.getSelectionModel().getSelectedIndex()).setDestination(hibernateController.getEntityById(Destination.class, destination.getId()));
        hibernateController.update(cargoListDestField.getItems().get(cargoListDestField.getSelectionModel().getSelectedIndex()));
        loadDestinations();
    }

    public void deleteDestination(ActionEvent actionEvent) {

        Truck tr = hibernateController.getEntityById(Truck.class, destinationListField.getItems().get(destinationListField.getSelectionModel().getSelectedIndex()).getTruck().getId());
        tr.setCurrentDestination(null);
        hibernateController.update(tr);

        Cargo ca = hibernateController.getEntityById(Cargo.class, destinationListField.getItems().get(destinationListField.getSelectionModel().getSelectedIndex()).getCargo().getId());
        ca.setDestination(null);
        hibernateController.update(ca);

        while(!destinationListField.getItems().get(destinationListField.getSelectionModel().getSelectedIndex()).getResponsibleManagers().isEmpty()){
            Manager ma =hibernateController.getEntityById(Manager.class, destinationListField.getItems().get(destinationListField.getSelectionModel().getSelectedIndex()).getResponsibleManagers().get(0).getId());
            ma.getMyManagedDestinations().remove(destinationListField.getItems().get(destinationListField.getSelectionModel().getSelectedIndex()));
            hibernateController.update(ma);
            destinationListField.getItems().get(destinationListField.getSelectionModel().getSelectedIndex()).getResponsibleManagers().remove(0);
        }

        hibernateController.deleteById(Destination.class, destinationListField.getItems().get(destinationListField.getSelectionModel().getSelectedIndex()).getId());
        //hibernateController.delete(destinationListField.getItems().get(destinationListField.getSelectionModel().getSelectedIndex()));
        loadDestinations();
    }

    public void updateDestination(ActionEvent actionEvent) {
        int i = destinationListField.getSelectionModel().getSelectedIndex();

        if(cargoListField.getSelectionModel().getSelectedIndex()!=-1) {
            Cargo ca = hibernateController.getEntityById(Cargo.class, destinationListField.getItems().get(i).getCargo().getId());
            ca.setDestination(null);
            hibernateController.update(ca);
            destinationListField.getItems().get(i).setCargo(cargoListDestField.getItems().get(i));
        }

        if(truckListField.getSelectionModel().getSelectedIndex()!=-1) {
            Truck tr = hibernateController.getEntityById(Truck.class, destinationListField.getItems().get(i).getTruck().getId());
            tr.setCurrentDestination(null);
            hibernateController.update(tr);
            destinationListField.getItems().get(i).setTruck(truckListDestField.getItems().get(i));
        }

        destinationListField.getItems().get(i).setStartCity(startCityField.getText());
        destinationListField.getItems().get(i).setEndCity(endCityField.getText());

        destinationListField.getItems().get(i).setStartLn(Long.parseLong(startLnField.getText()));
        destinationListField.getItems().get(i).setStartLat(Long.parseLong(startLatField.getText()));
        destinationListField.getItems().get(i).setEndLn(Long.parseLong(endLnField.getText()));
        destinationListField.getItems().get(i).setEndLat(Long.parseLong(endLatField.getText()));

        destinationListField.getItems().get(i).setDateUpdated(LocalDate.now());

        hibernateController.update(destinationListField.getItems().get(i));
        loadDestinations();
    }

    public void createCheckpoint(ActionEvent actionEvent) {
        Checkpoint checkpoint = new Checkpoint(titleCheckpointField.getText(), longStopCheck.isSelected());
        hibernateController.create(checkpoint);
        loadCheckpoints();
    }

    public void updateCheckpoint(ActionEvent actionEvent) {
        int i = checkpointListField.getSelectionModel().getSelectedIndex();

        checkpointListField.getItems().get(i).setTitle(titleCheckpointField.getText());
        checkpointListField.getItems().get(i).setLongStop(longStopCheck.isSelected());

        hibernateController.update(checkpointListField.getItems().get(i));
        loadCheckpoints();
    }

    public void deleteCheckpoint(ActionEvent actionEvent) {
        //hibernateController.delete(checkpointListField.getItems().get(checkpointListField.getSelectionModel().getSelectedIndex()));
        hibernateController.deleteById(Checkpoint.class, checkpointListField.getItems().get(checkpointListField.getSelectionModel().getSelectedIndex()).getId());
        loadCheckpoints();
    }

    public void loadUsers() {
        List<User> Ulist = new ArrayList<>(hibernateController.getAllRecords(User.class));
        userListField.getItems().clear();
        userListField.getItems().addAll(Ulist);
    }

    public void loadTrucks() {
        List<Truck> Tlist = new ArrayList<>(hibernateController.getAllRecords(Truck.class));
        truckListField.getItems().clear();
        truckListField.getItems().addAll(Tlist);
    }

    public void loadCargo() {
        List<Cargo> Clist = new ArrayList<>(hibernateController.getAllRecords(Cargo.class));
        cargoListField.getItems().clear();
        cargoListField.getItems().addAll(Clist);
    }

    public void loadDestinations() {
        List<Destination> Dlist = new ArrayList<>(hibernateController.getAllRecords(Destination.class));
        destinationListField.getItems().clear();
        destinationListField.getItems().addAll(Dlist);

        List<Checkpoint> Cplist = new ArrayList<>(hibernateController.getAllRecords(Checkpoint.class));
        unAssignedCheckpointList.getItems().clear();
        assignedCheckpointList.getItems().clear();
        for (int i = 0; i < Cplist.size(); i++) {
            if (Cplist.get(i).getDestination() != null) {
                Cplist.remove(i);
                i--;
            }
        }
        unAssignedCheckpointList.getItems().addAll(Cplist);

        List<Truck> Tlist = new ArrayList<>(hibernateController.getAllRecords(Truck.class));
        truckListDestField.getItems().clear();
        for (int i = 0; i < Tlist.size(); i++) {
            if (Tlist.get(i).getCurrentDestination() != null) {
                Tlist.remove(i);
                i--;
            }
        }
        truckListDestField.getItems().addAll(Tlist);

        List<User> Ulist = new ArrayList<>(hibernateController.getAllRecords(User.class));
        unAssignedManagers.getItems().clear();
        assignedManagers.getItems().clear();
        for(int i =0;i<Ulist.size();i++){
            if(Ulist.get(i).getClass()==Driver.class){
                Ulist.remove(i);
                i--;
            }
            else{
                unAssignedManagers.getItems().add(hibernateController.getEntityById(Manager.class, Ulist.get(i).getId()));
            }
        }

        List<Cargo> Clist = new ArrayList<>(hibernateController.getAllRecords(Cargo.class));
        for (int i = 0; i < Clist.size(); i++) {
            if (Clist.get(i).getDestination() != null) {
                Clist.remove(i);
                i--;
            }
        }
        cargoListDestField.getItems().clear();
        cargoListDestField.getItems().addAll(Clist);

    }

    public void loadDestinationsSelected(){
        List<Checkpoint> Cplist = new ArrayList<>(hibernateController.getAllRecords(Checkpoint.class));
        unAssignedCheckpointList.getItems().clear();
        assignedCheckpointList.getItems().clear();
        List<Checkpoint> Cplist2 = new ArrayList<>(destinationListField.getItems().get(destinationListField.getSelectionModel().getSelectedIndex()).getCheckpointList());
        for (int i = 0; i < Cplist2.size(); i++) {
            assignedCheckpointList.getItems().add(hibernateController.getEntityById(Checkpoint.class, Cplist2.get(i).getId()));
        }
        for (int i = 0; i < Cplist.size(); i++) {
            if (Cplist.get(i).getDestination() != null) {
                Cplist.remove(i);
                i--;
            }
        }
        if(!Cplist.isEmpty()) {
            unAssignedCheckpointList.getItems().addAll(Cplist);
        }

        List<Truck> Tlist = new ArrayList<>(hibernateController.getAllRecords(Truck.class));
        truckListDestField.getItems().clear();
        for (int i = 0; i < Tlist.size(); i++) {
            if (Tlist.get(i).getCurrentDestination() != null) {
                Tlist.remove(i);
                i--;
            }
        }
        truckListDestField.getItems().addAll(Tlist);

        List<Manager> Mlist = new ArrayList<>(hibernateController.getAllRecords(Manager.class));
        unAssignedManagers.getItems().clear();
        assignedManagers.getItems().clear();
        for (Manager manager : Mlist) {
            boolean ttest = false;

            if(manager.getMyManagedDestinations()!=null) {
                for (Destination destination : manager.getMyManagedDestinations()) {
                    if (destination == destinationListField.getItems().get(destinationListField.getSelectionModel().getSelectedIndex())) {
                        ttest = true;
                        break;
                    }
                }
            }

            if (ttest) {
                assignedManagers.getItems().add(hibernateController.getEntityById(Manager.class, manager.getId()));
            } else {
                unAssignedManagers.getItems().add(hibernateController.getEntityById(Manager.class, manager.getId()));
            }
        }

        List<Cargo> Clist = new ArrayList<>(hibernateController.getAllRecords(Cargo.class));
        for (int i = 0; i < Clist.size(); i++) {
            if (Clist.get(i).getDestination() != null) {
                Clist.remove(i);
                i--;
            }
        }
        cargoListDestField.getItems().clear();
        cargoListDestField.getItems().addAll(Clist);
    }

    public void loadCheckpoints() {
        List<Checkpoint> Cplist = new ArrayList<>(hibernateController.getAllRecords(Checkpoint.class));
        checkpointListField.getItems().clear();
        checkpointListField.getItems().addAll(Cplist);
    }

    public void loadAll(){
        loadUsers();
        loadTrucks();
        loadCargo();
        loadDestinations();
        loadCheckpoints();
        if(hibernateController.getEntityById(User.class, this.loggedUser.getId()).getClass()==Manager.class){
            userManagementTab.setDisable(false);
            Manager manager = hibernateController.getEntityById(Manager.class, this.loggedUser.getId());

            assignCheckpointButton.setDisable(false);
            unAssignCheckpointButton.setDisable(false);
            assignCheckpointButton.setVisible(true);
            unAssignCheckpointButton.setVisible(true);

            createCheckpointButton.setDisable(false);
            createTruckButton.setDisable(false);
            addDestinationButton.setDisable(false);
            addCargoButton.setDisable(false);

            createCheckpointButton.setVisible(true);
            createTruckButton.setVisible(true);
            addDestinationButton.setVisible(true);
            addCargoButton.setVisible(true);

            updateCheckpointButton.setDisable(false);
            updateTruckButton.setDisable(false);
            updateDestinationButton.setDisable(false);
            updateCargoButton.setDisable(false);

            updateCheckpointButton.setVisible(true);
            updateTruckButton.setVisible(true);
            updateDestinationButton.setVisible(true);
            updateCargoButton.setVisible(true);

            deleteCheckpointButton.setDisable(false);
            deleteTruckButton.setDisable(false);
            deleteDestinationButton.setDisable(false);
            deleteCargoButton.setDisable(false);

            deleteCheckpointButton.setVisible(true);
            deleteTruckButton.setVisible(true);
            deleteDestinationButton.setVisible(true);
            deleteCargoButton.setVisible(true);

            if(manager.isAdmin()){
                updateUserButton.setVisible(true);
                updateUserButton.setDisable(false);
                createUserButton.setVisible(true);
                createUserButton.setDisable(false);
                deleteUserButton.setVisible(true);
                deleteUserButton.setDisable(false);

                pswField.setDisable(false);
                pswField.setVisible(true);
                repPswField.setDisable(false);
                repPswField.setVisible(true);

                assignManagerButton.setDisable(false);
                unAssignManagerButton.setDisable(false);
                assignManagerButton.setVisible(true);
                unAssignManagerButton.setVisible(true);
            }
        }
    }
}
