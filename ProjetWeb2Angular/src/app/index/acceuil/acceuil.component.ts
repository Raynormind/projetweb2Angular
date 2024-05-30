import { Component, OnInit } from '@angular/core';
import { Forum } from '../models/forum.model';
import { MOCK_FORUMS } from './mock-data/mock-forums';
import { ForumService } from '../services/forum.service';

@Component({
  selector: 'app-acceuil',
  templateUrl: './acceuil.component.html',
  styleUrl: './acceuil.component.css'
})
export class AcceuilComponent implements OnInit {
  listForums: Forum[] = MOCK_FORUMS;

  constructor(private forumService: ForumService) {}

  ngOnInit(): void {
    this.getForums();
  }

  getForums(): void {
    this.forumService.getForums().subscribe(forums => this.listForums = forums);
  }
}
