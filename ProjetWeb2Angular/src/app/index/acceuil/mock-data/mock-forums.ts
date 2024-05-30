
import { Forum } from "../../models/forum.model";



export const MOCK_FORUMS: Forum[] = [
  new Forum(1, 'Forum de discussion en Français', 'Français', 'Culture française'),
  new Forum(2, 'Forum de discussion en Anglais', 'Anglais', 'Culture anglaise'),
  new Forum(3, 'Forum de discussion en Espagnol', 'Espagnol', 'Culture espagnole'),
  new Forum(4, 'Forum de discussion en Japonais', 'Japonais', 'Culture japonaise'),
  new Forum(5, 'Forum de discussion en Allemand', 'Allemand', 'Culture allemande')
];
