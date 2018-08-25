import 'package:flutter/services.dart';

import 'audio_visualizer.dart';


class Audio {

  static const MethodChannel _visualizerChannel =
  const MethodChannel('soundvisualization');



  static AudioVisualizer audioVisualizer() {
    return new AudioVisualizer(
      channel: _visualizerChannel,
    );
  }
}