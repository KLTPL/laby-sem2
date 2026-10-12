class PascalTriangleRow {
private:
  int *row_;
  int n_;
  int *createRow(int n);

public:
  PascalTriangleRow(int n);
  ~PascalTriangleRow();
  int getVal(int k);
};
