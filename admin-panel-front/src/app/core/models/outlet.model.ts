export interface Outlet {
  id?: number; // Изменено на number, так как значение 2 это число
  name?: string;
  defaultNotification?: string;
  imageUrl?: string;
  latitude1?: number;
  longitude1?: number;
  latitude2?: number;
  longitude2?: number;
  category?: string;
  mall?: {
    id: number;
  };
}
