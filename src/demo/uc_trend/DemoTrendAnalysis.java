package demo.uc_trend;

import controller.TrendAnalysisController;
import dto.FilterForm;
import dto.TrendRequest;
import model.*;

import java.io.File;
import java.time.LocalDate;
import java.util.Scanner;

/**
 * Demo tuong buoc: Trend Analysis (UC-2.2)
 * - Co san du lieu trong FakeDatabase
 * - Tung buoc nhan Enter de tiep tuc
 * - Chi nhap lieu khi actor (Admin) can nhap
 */
public class DemoTrendAnalysis {

    private static final Scanner sc = new Scanner(System.in);
    private static final TrendAnalysisController controller = new TrendAnalysisController();
    private static String timeRange;
    private static LocalDate startDate;
    private static LocalDate endDate;
    private static String selectedCategory;
    private static DashboardResponse lastResponse;
    private static long lastResultId = -1;

    public static void main(String[] args) {
        System.out.println("=====================================================");
        System.out.println("DEMO UC-20: PHAN TICH XU HUONG (Trend Analysis)");
        System.out.println("=====================================================");
        System.out.println("Bam Enter de tiep tuc qua tung buoc.");
        System.out.println("Khi gap 'nhap' -> go gia tri roi Enter.\n");

        chayScenarioA();
    }

    private static void chayScenarioA() {
        TrendRequest request;

        while (true) {
            /* -------------------- B1 -------------------- */
            enter();
            System.out.println("--- BUOC 1: Admin truy cap he thong, chon \"Phan tich xu huong\" ---");

            /* -------------------- B2 -------------------- */
            enter();
            System.out.println("--- BUOC 2 (HT): Hien thi form bo loc va dashboard mac dinh ---");
            controller.getDefaultDashboard().render();

            /* -------------------- B3 -------------------- */
            System.out.println("\n--- BUOC 3: Chon khoang thoi gian phan tich ---");
            System.out.print("  Nhap ngay bat dau (yyyy-MM-dd): ");
            LocalDate s = null, e = null;
            try { s = LocalDate.parse(sc.nextLine().trim()); } catch (Exception ex) {
                System.out.println("  Sai dinh dang! Lay mac dinh 2026-01-01");
                s = LocalDate.of(2026, 1, 1);
            }
            System.out.print("  Nhap ngay ket thuc (yyyy-MM-dd): ");
            try { e = LocalDate.parse(sc.nextLine().trim()); } catch (Exception ex) {
                System.out.println("  Sai dinh dang! Lay mac dinh 2026-06-30");
                e = LocalDate.of(2026, 6, 30);
            }
            if (s.isAfter(e)) { System.out.println("  Loi: ngay bat dau > ngay ket thuc! Thoat.\n"); return; }
            long days = java.time.temporal.ChronoUnit.DAYS.between(s, e);
            if (days < 7) { System.out.println("  Loi: can >= 7 ngay! Thoat.\n"); return; }
            startDate = s; endDate = e;
            timeRange = days <= 31 ? "1_THANG" : days <= 93 ? "3_THANG" : days <= 186 ? "6_THANG" : "1_NAM";

            /* -------------------- B4 -------------------- */
            enter();
            System.out.println("--- BUOC 4 (HT): Cap nhat bo loc ---");
            System.out.println("  Khoang: " + startDate + " -> " + endDate + "\n");

            /* -------------------- B5 -------------------- */
            System.out.println("--- BUOC 5: Admin chon danh muc ---");
            System.out.print("  Nhap danh muc (Action/Comedy/Fantasy): ");
            selectedCategory = sc.nextLine().trim();
            if (!selectedCategory.equals("Action") && !selectedCategory.equals("Comedy") && !selectedCategory.equals("Fantasy")) {
                System.out.println("  Loi: chi Action, Comedy, Fantasy. Lay Action.\n");
                selectedCategory = "Action";
            }

            /* -------------------- B6 -------------------- */
            enter();
            System.out.println("--- BUOC 6 (HT): Cap nhat bo loc ---");
            System.out.println("  Danh muc: " + selectedCategory + "\n");

            /* -------------------- B7 -------------------- */
            System.out.println("--- BUOC 7: Nhan nut \"Phan tich\" ---");
            System.out.print("  Nhan Enter de phan tich..."); sc.nextLine();

            request = new TrendRequest(timeRange, selectedCategory, startDate, endDate);

            try {
                /* -------------------- 7a -------------------- */
                System.out.println("\n--- BUOC 7a (HT): Kiem tra tham so? ---");
                enter();

                /* -------------------- 8 -------------------- */
                System.out.println("--- BUOC 8 (HT): Thu thap du lieu tu database ---");
                System.out.println("  (EF1: Neu loi DB -> thong bao thu lai)");
                System.out.println("  (EF2: Neu timeout >30s -> thong bao qua thoi gian)");
                enter();

                lastResponse = controller.analyzeTrend(request);
                AnalysisResult result = lastResponse.getAnalysisResult();
                lastResultId = result.getResultID();

                /* -------------------- 8a -------------------- */
                System.out.println("\n--- BUOC 8a (HT): Kiem tra du lieu du? --- Du");
                enter();

                /* -------------------- 9 -------------------- */
                System.out.println("--- BUOC 9 (HT): Phan tich du lieu theo thuat toan ---");
                enter();

                /* -------------------- 10 -------------------- */
                System.out.println("--- BUOC 10 (HT): Tinh diem xu huong tang-giam-on dinh ---");
                enter();

                /* -------------------- 11 -------------------- */
                System.out.println("--- BUOC 11 (HT): Tong hop ket qua va luu vao database ---");
                enter();

                /* -------------------- 12 -------------------- */
                System.out.println("--- BUOC 12 (HT): Hien thi Dashboard voi bieu do va so lieu ---\n");
                System.out.println("========== DASHBOARD ==========");
                lastResponse.getDashboard().render();
                TrendScore score = result.getTrendScore();
                System.out.println("  Diem: " + String.format("%.1f", score.getScore()));
                System.out.println("  Xu huong: " + score.getDirection());
                System.out.println("  Do tin cay: " + String.format("%.1f%%", score.getConfidence()));
                System.out.println("  Ket luan: " + score.getInterpretation());
                TrendReport rp = lastResponse.getTrendReport();
                if (rp != null) {
                    System.out.println("  Du bao: " + rp.getForecast().getInterpretation());
                    for (String rec : rp.getRecommendations()) System.out.println("  - " + rec);
                }
                System.out.println();

                /* -------------------- 13 -------------------- */
                System.out.println("--- BUOC 13: Admin xem ket qua tren dashboard ---");
                enter();

                /* -------------------- AF3 -------------------- */
                System.out.println("--- AF3: Thay doi bo loc? ---");
                System.out.print("  Co muon thay doi bo loc? (y/N): ");
                String af3 = sc.nextLine().trim();
                if (af3.equalsIgnoreCase("y")) {
                    System.out.println("  => Quay lai buoc 3.\n");
                    continue;
                }
                System.out.println("  => Khong doi, tiep tuc.\n");

                /* -------------------- 14 -------------------- */
                System.out.println("--- BUOC 14: Admin nhan xuat bao cao ---");
                enter();

                /* -------------------- 15 -------------------- */
                System.out.println("--- BUOC 15 (HT): Tao file bao cao, cho phep tai xuong ---");
                xuatBaoCao();

                System.out.println("\n  === KET THUC USE CASE ===\n");
                break;

            } catch (IllegalArgumentException ex) {
                System.out.println("\n--- BUOC 7b (HT): Hien thi loi tham so khong hop le: " + ex.getMessage() + " => Quay lai buoc 3.");
                continue;
            } catch (java.util.NoSuchElementException ex) {
                System.out.println("\n--- BUOC 8b (HT): Hien thi khong du du lieu: " + ex.getMessage() + " => Quay lai buoc 3.");
                continue;
            } catch (Exception ex) {
                System.out.println("EF1. Loi Database - Thong bao thu lai / EF2. Timeout >30s - Thong bao xu ly qua thoi gian");
                System.out.println("  Chi tiet: " + ex.getMessage());
                break;
            }
        }
    }

    private static void enter() {
        System.out.print("  (Nhan Enter de tiep tuc...)");
        sc.nextLine();
        System.out.println();
    }

    private static void xuatBaoCao() {
        if (lastResultId < 0) return;
        try {
            File f = controller.exportReport(lastResultId);
            if (f != null)
                System.out.println("  Xuat thanh cong: " + f.getPath());
            else
                System.out.println("  Xuat that bai!");
        } catch (Exception e) {
            System.out.println("EF3. Loi xuat bao cao - Thong bao loi: " + e.getMessage());
        }
    }
}
