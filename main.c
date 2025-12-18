#include "bigint.h"

int main() {
    int num1[MAXLEN + 5] = { 0 };//��һ��������������������
    int num2[MAXLEN + 5] = { 0 };//�ڶ���������������������:����Ӽ����㣩
    int result[MAXLEN + 10] = { 0 };//�����������MAXLEN + 5 �� MAXLEN + 10����߽���ʴ���
    int choice;

    while (1) {
        MENU();
        printf("��ѡ�������");
        scanf("%d", &choice);
        getchar(); // ���ջ���

        switch (choice) {
        case 1:
            CZSSR(num1);
            break;
        case 2:
            printf("��ǰ������Ϊ��");
            CZSSC(num1);
            break;
        case 3:
            printf("������ڶ�����������\n");
            CZSSR(num2);
            CZSADD(num1, num2, result);
            printf("�ӷ������");
            CZSSC(result);
            break;
        case 4:
            printf("������ڶ�����������С�ڵ��ڵ�һ������\n");
            CZSSR(num2);
            CZSSUB(num1, num2, result);
            printf("���������");
            CZSSC(result);
            break;
        case 5:
            JZZH2(num1);
            break;
        case 6:
            JZZH16(num1);
            break;
        case 7:
            CZSWJ(num1, "longint.txt");//���泤��                                                                                                                                                                                                        �����ļ�
            break;
        case 8: {
            int divisor;
            printf("���������ڳ�������������int����");
            if (scanf("%d", &divisor) != 1) {
                printf("������Ч��\n");
                // �������뻺��
                int ch; while ((ch = getchar()) != '\n' && ch != EOF) {}
                break;
            }
            if (divisor <= 0) {
                printf("��������Ϊ��������\n");
                break;
            }
            int quotient[MAXLEN + 10] = { 0 };
            int rem = CZSDIV(num1, divisor, quotient);
            if (rem >= 0) {
                printf("��Ϊ��");
                CZSSC(quotient); // ʹ�����е��������
                printf("����Ϊ��%d\n", rem);
            }
            else {
                printf("����ʧ�ܡ�\n");
            }
            break;
        }

        case 0:
            printf("����������ټ���\n");
            return 0;
        default:
            printf("��Чѡ�����������롣\n");
        }
    }
}

static void print_menu_border() {
    putchar('+');
    for (int i = 0; i < 48; ++i) putchar('=');
    puts("+");
}

static void print_menu_line(const char* text) {
    printf("| %-48s |\n", text);
}

void MENU() {
    const char* title = "�������ܲ���ϵͳ";
    const char* subtitle = "��ѡ����Ҫ�Ĺ��� (0 �˳�)";
    const char* options[] = {
        "1. ���볤����",
        "2. ���������",
        "3. �������ӷ�",
        "4. ����������",
        "5. ʮ����ת������",
        "6. ʮ����תʮ������",
        "7. ���泤�������ļ�",
        "8. ����������",
        "0. �˳�����"
    };
    int option_count = sizeof(options) / sizeof(options[0]);

    printf("\n");
    print_menu_border();
    print_menu_line("");
    print_menu_line(title);
    print_menu_line(subtitle);
    print_menu_line("");
    print_menu_border();
    for (int i = 0; i < option_count; ++i) {
        print_menu_line(options[i]);
    }
    print_menu_border();
    print_menu_line("��ʾ: ������ŷ�ת���������ٸ�ȷ��");
    print_menu_border();
}
