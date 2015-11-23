/**
 * Created by Administrator on 2015/11/23 0023.
 */
function get(s){
    if(s=='<') return "&lt;";
    if(s=='>') return "&gt;";
    return s;
}
function green(s){
    if(s==' '||s=='	') return s;
    return "<front style='background-color:#2bbb2b'>"+get(s)+"</front>";
}
function red(s){
    if(s==' '||s=='	') return s;
    return "<front style='background-color:#F37575'>"+get(s)+"</front>";
}

function comp(s1,s2){
    var d=[[],[]];//距离数组
    var p=[];//前驱数组//  ul 0  up 1  left 2
    var len1=s1.length;
    var len2=s2.length;
    if(len1*len2>=100000000) {
        alert("代码太长，不能比较...");
        return {s1:s1,s2:s2};
    }
    var i,j;
    for(i=0;i<len1;i++){
        p[i]=[];
        for(j=0;j<len2;j++){
            if(i==0&&j==0){
                d[i][j]=(s1.charAt(i)!=s2.charAt(j));
                p[i][j]=0;
            }else if(i==0){
                d[i][j]=j;
                p[i][j]=2;
            }else if(j==0){
                d[i%2][0]=i;
                p[i][j]=1;
            }else{
                var up=d[(i-1)%2][j] + 1;
                var left=d[i%2][j - 1] + 1;
                var ul= d[(i-1)%2][j - 1] + (s1.charAt(i)!=s2.charAt(j));
                if(up<=left&&up<=ul){
                    d[i%2][j]=up;
                    p[i][j]=1;
                }else if(left<=up&&left<=ul){
                    d[i%2][j]=left;
                    p[i][j]=2;
                }else{
                    d[i%2][j]=ul;
                    p[i][j]=0;
                }
            }
        }
    }
    var ret1="",ret2="";
    i=len1-1;
    j=len2-1;
    while(1){
        if(p[i][j]==0){
            if(s1.charAt(i)==s2.charAt(j)){
                ret1=get(s1.charAt(i))+ret1;
                ret2=get(s2.charAt(j))+ret2;
            }else{
                ret1=red(s1.charAt(i))+ret1;
                ret2=green(s2.charAt(j))+ret2;
            }
            i--;j--;
        }else if(p[i][j]==1){
            ret1=red(s1.charAt(i))+ret1;
            i--;
        }else{
            ret2=green(s2.charAt(j))+ret2;
            j--;
        }
        if(i<0||j<0) break;
    }
    return {s1:ret1,s2:ret2};
}