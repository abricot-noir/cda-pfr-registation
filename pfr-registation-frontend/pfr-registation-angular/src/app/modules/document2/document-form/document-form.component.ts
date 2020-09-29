import { Component, OnInit } from '@angular/core';
import {IDocument} from '../../../interfaces/idocument';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {DocumentService} from '../../../services/document.service';

@Component({
  selector: 'app-document-form',
  templateUrl: './document-form.component.html',
  styleUrls: ['./document-form.component.css']
})
export class DocumentFormComponent implements OnInit {

  document2: IDocument;
  documentList2: Array<IDocument> = [];
  documentForm2: FormGroup;
  date1: Date = new Date();
  date2: Date = new Date();
  todayNumber: number = Date.now();
  todayDate: Date = new Date();
  todayString: string = new Date().toDateString();
  todayISOString: string = new Date().toISOString();
  todayNumber2: number = Date.now();
  todayDate2: Date = new Date();
  todayString2: string = new Date().toDateString();
  todayISOString2: string = new Date().toISOString();

  constructor(private route: ActivatedRoute, private router: Router, private documentService: DocumentService, private fb: FormBuilder) {
    this.initForm();
  }
  initForm(): void {
    //   this.documentForm2 = this.fb.group({
    //     nomDocument: ['facture pompe n°7', [Validators.required]],
    //     categorieDocument: ['facture', [Validators.required]],
    //     dateAjoutDocument: [this.date1, [Validators.required]],
    //     dateDerniereModificationDocument: [this.date2, [Validators.required]],
    //     descriptionDocument: ['Ceci est la facture de la pompe n°7', [Validators.required]],
    //     commentairesDocument: ['Rien de particulier à signaler', [Validators.required]],
    //     matriculeUtilisateur: ['RESP001', [Validators.required]],
    //
    //   });
    // }
    this.documentForm2 = new FormGroup({
      nomDocument: new FormControl(),
      categorieDocument: new FormControl(),
      dateAjoutDocument: new FormControl(),
      dateDerniereModificationDocument: new FormControl(),
      descriptionDocument: new FormControl(),
      commentairesDocument: new FormControl(),
      matriculeUtilisateur: new FormControl(),
    });
  }
  ngOnInit(): void {
    this.documentService.findAll().subscribe(result => {
      this.documentList2 = result;
    });
  }
  ajouterDocument() {
    this.document2 = (this.documentForm2.value as IDocument);
    console.log(this.documentForm2.value);
    console.log(this.document2);
    this.documentService.save(this.document2).subscribe(result => this.goToDocumentList());
  }
  goToDocumentList(){
    this.router.navigate(['/document/liste']);
  }
}
