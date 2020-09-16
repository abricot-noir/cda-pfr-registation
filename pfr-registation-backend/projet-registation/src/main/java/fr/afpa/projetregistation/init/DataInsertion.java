package fr.afpa.projetregistation.init;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.afpa.projetregistation.dao.IDocumentDao;
import fr.afpa.projetregistation.dao.IMaterielDao;
import fr.afpa.projetregistation.dao.IUtilisateurDao;
import fr.afpa.projetregistation.dto.DocumentDto;
import fr.afpa.projetregistation.dto.MaterielDto;
import fr.afpa.projetregistation.dto.EvenementDto;
import fr.afpa.projetregistation.dto.UtilisateurDto;
import fr.afpa.projetregistation.entity.UtilisateurEntity;
import fr.afpa.projetregistation.service.IDocumentService;
import fr.afpa.projetregistation.service.IEvenementService;
import fr.afpa.projetregistation.service.IMaterielService;
import fr.afpa.projetregistation.service.IUtilisateurService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DataInsertion {

	@Autowired
	IMaterielDao materielDao;

	@Autowired
	IMaterielService materielService;

	@Autowired
	IUtilisateurService utilisateurService;
	
	@Autowired
	IUtilisateurDao udao;

	@Autowired
	IDocumentDao documentDao;

	@Autowired
	IDocumentService documentService;
	
	@Autowired
	IEvenementService eserv;

	@PostConstruct
	public void cdaInit() {

		this.insertionMateriel();
		this.insertionUtilisateurs();

		//METHODE POUR AJOUTER 3 DOCUMENTS MAX ALICE TO DO
		
		Date dateAjoutDoc = new Date();
		Date dateDerniereModificationdoc = new Date();
		
		DocumentDto doc = new DocumentDto("facture entretien cuve n°3", "facture", dateAjoutDoc,
				dateDerniereModificationdoc, "facture qui correspond au dernier entretien de la cuve n°3.",
				"rien à signaler, fonctionnement de la cuve n°3 ok.");
		documentService.ajouterDocument(doc);


		
		
		//Ajout de 2 évènements
		insertionEvenements();

	}

	public void insertionMateriel() {
		// Ajout de trois matériels.

		String pattern = "dd-MM-yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		Date achat = null;
		try {
			achat = simpleDateFormat.parse("15-01-1989");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		MaterielDto materiel = new MaterielDto("P001", "Orion", "PistoXC", 500,  1, achat, "pompe","pompe2");
		materielService.create(materiel);
		MaterielDto materiel2 = new MaterielDto("C001", "Neptune", "Cuve3000", 2000,  1, achat, "cuve","emplacement1");
		materielService.create(materiel2);
		MaterielDto materiel3 = new MaterielDto("P002", "Orion", "PistoXC", 500,  1, achat, "pompe", "pompe2");
		materielService.create(materiel3);
		MaterielDto materiel4 = new MaterielDto("P003", "Orion", "PistoXC", 500,  1, achat, "pompe", "pompe2");
		materielService.create(materiel4);
		MaterielDto mat3 = new MaterielDto("MC001", "Mars", "Cafe3000", 200,  1, achat, "MACHINE A CAFE","allée 1");
		materielService.create(mat3);
	}

	public void insertionUtilisateurs() {
		String pattern = "dd-MM-yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		Date dateNaissanceTest = null;
		try {
			dateNaissanceTest = simpleDateFormat.parse("15-01-1989");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// INSERTION DE 2 USER AVEC COUPLE CONNEXION ET ADRESSE
		UtilisateurDto utilisateur = new UtilisateurDto("EMP001", "pwd1", "nomEMP", "prenomEMP", dateNaissanceTest,
				2000.0, "employe@gmail.com", "06.06.06.06.06", false, 1, "rue de l'employé", "complément1", "59000",
				"LILLE", "France");
		utilisateurService.create(utilisateur);

		utilisateur = new UtilisateurDto("RESP001", "pwd2", "nomRESP", "prenomRESP", dateNaissanceTest, 2500.0,
				"responsable@gmail.com", "07.07.07.07.07", true, 10, "rue du responsable", "complément2", "59100",
				"ROUBAIX", "France");
		utilisateurService.create(utilisateur);

	}
	
	public void insertionEvenements() {
		String pattern = "dd-MM-yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String d1 = "30-08-2020";
		String d2 = "01-09-2020";
		Date date1 = null;
		Date date2 = null;
		int diff = 0;
		
		try {
			date1 = sdf.parse(d1);
			date2 = sdf.parse(d2);
			long mse = date2.getTime()-date1.getTime();
			diff = (int)TimeUnit.SECONDS.convert(mse, TimeUnit.MILLISECONDS); 
		}catch(ParseException e) {
			log.warn("Erreur lors du parsing des dates lors du test unitaire !");
		}

		Optional<UtilisateurEntity> optUser = udao.findById("EMP001");
		
		if(optUser.isPresent()) {
			UtilisateurEntity user = optUser.get();
			log.debug(user.getMatricule());
			EvenementDto ee = EvenementDto.builder().type("Révision")
					.description("test1")
					.date_debut(date1)
					.date_fin(date2)
					.duree(diff)
					.user(user).build();
			 
			if(eserv.getByType("Révision") == null) {
				ee = eserv.create(ee);
			}else {
				log.info("Révision n'a été ajouté !");
			}
		}
		
//		String d3 = "01-09-2020";
//		String d4 = "05-09-2020";
//		Date date3 = null;
//		Date date4 = null;
//		diff = 0;
//		
//		try {
//			date3 = sdf.parse(d3);
//			date4 = sdf.parse(d4);
//			long mse = date4.getTime()-date3.getTime();
//			diff = (int)TimeUnit.SECONDS.convert(mse, TimeUnit.MILLISECONDS); 
//		}catch(ParseException e) {
//			log.warn("Erreur lors du parsing des dates lors du test unitaire !");
//		}
//		
//		if(optUser.isPresent()) {
//			UtilisateurEntity user = optUser.get();
//			EvenementDto ee = EvenementDto.builder().type("Inspection")
//					.description("test2")
//					.date_debut(date3)
//					.date_fin(date4)
//					.duree(diff)
//					.user(user).build();
//			
//			if(eserv.getByType("Inspection") == null) {
//				ee = eserv.create(ee);
//			}else {
//				log.info("Inspection n'a été ajouté !");
//			}
//		}  
		
		String d5 = "14-09-2020";
		String d6 = "01-01-2021";
		Date date5 = null;
		Date date6 = null;
		diff = 0;
		
		try {
			date5 = sdf.parse(d5);
			date6 = sdf.parse(d6);
			long mse = date6.getTime()-date5.getTime();
			diff = (int)TimeUnit.SECONDS.convert(mse, TimeUnit.MILLISECONDS); 
		}catch(ParseException e) {
			log.warn("Erreur lors du parsing des dates lors du test unitaire !");
		}
		
		if(optUser.isPresent()) {
			UtilisateurEntity user = optUser.get();
			EvenementDto ee = EvenementDto.builder().type("Inspection")
					.description("test3")
					.date_debut(date5)
					.date_fin(date6)
					.duree(diff)
					.user(user).build();
			
			if(eserv.getByType("Inspection") == null) {
				ee = eserv.create(ee);
			}else {
				log.info("Inspection n'a été ajouté !");
			}
		}  
	}

}
