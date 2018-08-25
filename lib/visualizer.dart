import 'package:flutter/widgets.dart';
import 'audio.dart';
import 'audio_visualizer.dart';



class Visualizer extends StatefulWidget {

  final Function(BuildContext context, List<int> fft) builder;

  Visualizer({
    this.builder,
  });

  @override
  _VisualizerState createState() => new _VisualizerState();
}

class _VisualizerState extends State<Visualizer> {

  AudioVisualizer visualizer;
  List<int> fft = const [0];

  @override
  void initState()
  {

    super.initState();
    visualizer = Audio.audioVisualizer()
      ..activate()
      ..addListener(
          fftCallback: (List<int> samples) {
            setState(() => fft = samples);
          }
      );
  }

  @override
  void dispose() {
    visualizer.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return widget.builder(context, fft);
  }
}