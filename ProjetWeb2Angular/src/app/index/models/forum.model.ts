export class Forum {
    id: number;
    titreForum: string;
    langue: string;
    interetsCulturels: string;
  
    constructor(id: number, titreForum: string, langue: string, interetsCulturels: string) {
      this.id = id;
      this.titreForum = titreForum;
      this.langue = langue;
      this.interetsCulturels = interetsCulturels;
    }
  }
  