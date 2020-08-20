package com.tml.sort.merge;

public class Merge {

	/**
	 *  归并排序原理：先拆分，再合并，归并排序首先将序列进行拆分直到每个序列不能拆分为止，
	 *  然后对序列进行两两合并(合并的过程需要排序),知道最终只存在一个序列为止
	 *
	 *  归并排序效率分析：归并排序运行的时间复杂度为O(nlogn) 空间代价为O(n)
	 *
	 *  最好情况和最差情况下时间复杂度均为O(nlogn),归并排序是一种稳定的外部排序算法
	 *
	 *  归并排序C 代码递归方式实现(当然可以对其采用迭代方式实现)
	 *
	 *  void mergeSort(int[] array,int n)
	 *  {
	 *        if(n>1) //当序列长度大于1时进行拆分
	 *        {
	 *        		int *list1 = array;
	 *        		int list1_size = 2/n;
	 *        		int *list2 = array+2/n;
	 *        		int list2_size = n-list1_size;
	 *
	 *        		mergeSort(list1,list1_size); //对序列进行拆分
	 *        		mergeSort(list2,list2_size);
	 *
	 *        		merging(list1,list1_size,list2,list2_size);//对两个序列进行合并
	 *        }
	 *  }
	 *
	 *  void merging(int *list1,int list1_size,int *list2,int list2_size)
	 *  {
	 *  	 int i,j,k,m;
	 *  	 int temp[maxsize];
	 *  	 i=j=k=0;
	 *  	while(i<list1_size&&j<list2_size) /将两个序列元素进行合并
	 *  	{
	 *      	if(list1[i]<list2[j])
	 *      	{
	 *      		temp[k++] = list1[i++];
	 *      	}else{
	 *      		temp[k++] = list2[j++];
	 *      	}
	 *      }
	 *
	 *      while(i<list1_size)  //将序列中剩余元素添加到temp中，两个序列中最多只会有一个序列包含剩余元素
	 *      {
	 *      	temp[k++] = list1[i++];
	 *      }
	 *
	 * 		while(i<list1_size)
	 *      {
	 *      	temp[k++] = list1[i++];
	 *      }
	 *
	 *      for(m=0,m<(list1_size+list2_size);m++)
	 *      {
	 *      	list1[m] = temp[m];
	 *      }
	 *  }
	 *
	 */
	
}
