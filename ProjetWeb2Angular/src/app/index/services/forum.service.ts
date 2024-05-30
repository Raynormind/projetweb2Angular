
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Forum } from '../models/forum.model';
import { MOCK_FORUMS } from '../acceuil/mock-data/mock-forums';

@Injectable({
  providedIn: 'root'
})
export class ForumService {

  constructor() { }

  getForums(): Observable<Forum[]> {
    return of(MOCK_FORUMS);
  }
}
