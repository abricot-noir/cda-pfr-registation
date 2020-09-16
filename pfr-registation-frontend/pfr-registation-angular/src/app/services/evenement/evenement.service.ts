import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Icalendrier } from 'src/app/interfaces/icalendrier';
import { Ievent } from 'src/app/interfaces/ievent';

@Injectable({
  providedIn: 'root'
})
export class EvenementService {
  calendrier: Icalendrier;
  private url: string = "http://localhost:8080/evenement";

  constructor(private http: HttpClient) { }

  public findByYearMonth(year: number, month: number, action:string): Observable<Icalendrier> {
    console.log(this.url+"/"+year+"/"+month+"/"+action);
    if(action.length<=1){
      return this.http.get<Icalendrier>(this.url+"/"+year+"/"+month+"/"+action);
    }
  }

/**  public findById(id: number): Observable<Imateriel> {
    return this.http.get<Imateriel>(this.url + "/" + id);
  }

  public addMateriel(mat: Imateriel) {
    return this.http.post<Imateriel>(this.url, mat);
  } */
}