import 'package:flutter/material.dart';
import 'audiofile_plugin.dart';
import 'storemedia.dart';

void main(){
  runApp(new MaterialApp(
    title: "Slider App",
    home: new EqualizerActivity(),
  ));
}

class EqualizerActivity extends StatefulWidget
{

  _EqualizerActivityState createState() => _EqualizerActivityState();

}

class _EqualizerActivityState extends State<EqualizerActivity>
{

  List<String> _tab;
  int upper=1;
  int lower=-1;

  List<double> _value= [0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0];

  List<double> _freq= [0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0];


  int _band=6;

  AudiofilePlugin equalizer=new AudiofilePlugin();

  @override
  void initState()
  {

    equalizer.equalizer();

    super.initState();

    getEqualizerBand().then(updateBand);
    getEqualizerLower().then(updateEqualizerLower);
    getEqualizerUpper().then(updateEqualizerUpper);
    getEqualizerBandLevel().then(updateEqualizerValue);
    getFreqLevel().then(updateFreq);
  }

  void updateBand(int band)
  {
    setState(() {
      _band=band;
      print("band ${_band}");
    });
  }

  void updateEqualizerLower(int tab)
  {

    setState(() {

       lower=tab;

    });

    print(lower.toString());
  }

  void updateEqualizerUpper(int tab)
  {

    setState(() {
      upper=tab;
    });

    print(upper.toString());
  }

  void updateEqualizerValue(List<String> tab)
  {


    int i;
    setState(() {

      for(i=0;i<tab.length;i++)
      {
        _value[i]= double.parse(tab[i]);
      }

    });


    print("Value"+_value.toString());
  }
  //double _value=0.0;

  //double _value1=0.0;

  void updateFreq(List<String> tab)
  {

    int i;
    setState(() {

      for(i=0;i<tab.length;i++)
        {
          _freq[i]= double.parse(tab[i]);
        }

    });

    print("FREQ"+_freq.toString());
  }



  onchanged(double value,int i)
  {
    setState(() {
      _value[i]=value;
      debugPrint(_value[i].toString());
    });
  }
/*

  onchanged1(List<double> value)
  {
    for(int i=0;i<value.length;i++)
    {

      setState(() {
       _value[i]=value[i];
       debugPrint(_value[i].toString());
     });
    }
  }

*/

  @override
  Widget build(BuildContext context) {
    return Scaffold(

      appBar: AppBar(
        title: Text("Effets sonores"),

      ),
      backgroundColor: Colors.white,
      body: new Container(
        child: new Center(
          child: new Column(

            children: <Widget>[
              Expanded(child: Container(),),
              RowFreqWidget(_band-1),
              RowWidget(_band-1),
              RowFreqWidget(_band-2),
              RowWidget(_band-2),
              RowFreqWidget(_band-3),
              RowWidget(_band-3),
              RowFreqWidget(_band-4),
              RowWidget(_band-4),
              RowFreqWidget(_band-5),
              RowWidget(_band-5),
              RowFreqWidget(_band-6),
              RowWidget(_band-6),
              RowFreqWidget(_band-7),
              RowWidget(_band-7),
              RowFreqWidget(_band-8),
              RowWidget(_band-8),
              RowFreqWidget(_band-9),
              RowWidget(_band-9),
              RowFreqWidget(_band-10),
              RowWidget(_band-10),
              Expanded(child: Container(),),
            ],
          ),
        ),
      ),
    );

  }


  Widget RowWidget(int i)
  {
    bool status=false;
    if(i>-1)
    {
      status=true;
    }

    return status? new Center(

      child: new Row(
        mainAxisSize: MainAxisSize.max,
        children: <Widget>[
          Expanded(child: Container(),),
          Text(" ${(lower/100).round()}db",style: TextStyle(color: Colors.blue,fontWeight:FontWeight.bold,fontSize: 16.0)),
          //Container(
          new Slider(
            min:lower/1,
            max:(upper/1),
            activeColor: Colors.pink,
            value: _value[i],
            onChanged: (double value){
              setState(() {
                _value[i]=value;
                equalizer.setBandLevel((_value[i]),i);
                debugPrint("EqualizerDebug $i"+_value[i].toString()+"lower is:"+lower.toString()+"upper is:"+upper.toString()+"autre valeur"+value.toString());
              });
            },


          ),

          Text("${(upper/100).round()}db",style: TextStyle(color: Colors.blue,fontWeight:FontWeight.bold,fontSize: 16.0),),
          Expanded(child: Container(),),
        ],
      ),
    ):Container();


  }


  Widget RowFreqWidget(int i)
  {
    bool status=false;
    if(i>-1)
    {
      status=true;
    }

    return status? new Center(child: Text("${(_freq[i]/1000).round()}MHZ",style: TextStyle(color: Colors.green,fontWeight:FontWeight.w200,fontSize: 16.0,fontStyle: FontStyle.italic),),):Container();


  }
}