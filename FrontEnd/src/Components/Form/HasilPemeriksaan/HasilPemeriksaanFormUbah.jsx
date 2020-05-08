import React from 'react';
import SirioForm from '../SirioForm';
import SirioButton from '../../Button/SirioButton';
import HasilPemeriksaanService from '../../../Services/HasilPemeriksaanService';
import RisikoService from '../../../Services/RisikoService';
import { NavLink, Redirect } from 'react-router-dom';
import { withRouter } from 'react-router-dom';
import RiskLevelService from "../../../Services/RiskLevelService";
import SirioField from "../SirioFormComponent/SirioField";
import TemuanRisikoService from "../../../Services/TemuanRisikoService";
import SirioConfirmButton from "../../Button/ActionButton/SirioConfirmButton";
import SirioComponentHeader from "../../Header/SirioComponentHeader";
import ComponentWrapper from "../../../Layout/ComponentWrapper";

class HasilPemeriksaanFormUbah extends React.Component {

    // Masukan user disimpan kedalam state sebelum dikirim ke backend
    constructor(props) {
        super(props);

        this.state = {
            idCurrentStatus: "",
            daftarKomponenPemeriksaan: [],
            daftarRisikoKategori1: [],
            daftarRisikoKategori2: [],
            daftarRisikoKategori3: [],
            daftarHistoriTemuan: [],
            filterKategori: "",
            kategoriType: "",
            riskLevelOptionList: [],
            riskOptionList: [],
            submitableDraft: false,
            submitable: false,
            redirect: false
        };

        this.renderData = this.renderData.bind(this);
        this.handleChangeKomponen = this.handleChangeKomponen.bind(this);
        this.handleSelectChangeKomponen = this.handleSelectChangeKomponen.bind(this);
        this.handleSelectChange = this.handleSelectChange.bind(this);
        this.handleSelectChangeRisiko = this.handleSelectChangeRisiko.bind(this);
        this.handleMultiFieldChange = this.handleMultiFieldChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.modifyFieldCount = this.modifyFieldCount.bind(this);
        this.setRedirect = this.setRedirect.bind(this);
        this.getInputDefinition = this.getInputDefinition.bind(this);
        this.submitable = this.submitable.bind(this);
        this.submitableDraft = this.submitableDraft.bind(this);
    }

    componentDidMount() {
        this.renderData();
    }

    setRedirect = () => {
        this.setState({
            redirect: true
        })
    };

    renderRedirect = () => {
        if (this.state.redirect) {
            return <Redirect to={{
                pathname: "/hasil-pemeriksaan",
                state: {
                    editSuccess: true
                }
            }} />
        }
    };

    async renderData() {
        const responseTemuanRisiko = await TemuanRisikoService.getHistoriTemuanRisikoKantorCabang(this.props.location.state.id);
        const daftarHistoriTemuan = responseTemuanRisiko.data.result;

        const responseRiskLevel = await RiskLevelService.getAll();
        const riskLevelList = responseRiskLevel.data.result.map(riskLevel => {
            return (
                {
                    label: riskLevel.namaLevel,
                    value: riskLevel.idLevel
                }
            )
        });

        const responseRisiko = await RisikoService.getAll();
        const risikoKategori1 = responseRisiko.data.result
            .filter(risiko => risiko.kategori === 1)
            .map(risiko => {
                return (
                    {
                        label: risiko.nama,
                        value: risiko.id
                    }
                )
            });
        const risikoKategori2 = responseRisiko.data.result
            .filter(risiko => risiko.kategori === 2)
            .map(risiko => {
                return (
                    {
                        label: risiko.nama,
                        value: risiko.id
                    }
                )
            });
        const risikoKategori3 = responseRisiko.data.result
            .filter(risiko => risiko.kategori === 3)
            .map(risiko => {
                return (
                    {
                        label: risiko.nama,
                        value: risiko.id
                    }
                )
            });

        const responseHasilPemeriksaan = await HasilPemeriksaanService.getHasilPemeriksaan(this.props.location.state.id);
        const idCurrentStatus = responseHasilPemeriksaan.data.result.idStatus;
        const daftarKomponen = responseHasilPemeriksaan.data.result.daftarKomponenPemeriksaan
            .map(komponen => {
                return (
                    {
                        id: komponen.id,
                        idRiskLevel: komponen.idRiskLevel,
                        submitableRiskLevel: [true,false],
                        risiko: komponen.risiko,
                        jumlahSampel: komponen.jumlahSampel,
                        errorJumlahSampel: "",
                        submitableJumlahSampel: [true,false],
                        jumlahPopulasi:"",
                        errorJumlahPopulasi:"",
                        submitableJumlahPopulasi: [true,false],
                        jumlahSampelError:"",
                        errorJumlahSampelError:"",
                        submitableJumlahSampelError: [true,false],
                        keteranganSampel: komponen.keteranganSampel,
                        errorKeteranganSampel: "",
                        submitableKeteranganSampel: [true,false],
                        daftarTemuanRisiko: (komponen.daftarTemuanRisikoTerdaftar.length > 0) ?
                            komponen.daftarTemuanRisikoTerdaftar
                            :
                            [{
                                keterangan: ""
                            }],
                        daftarRekomendasi: (komponen.daftarRekomendasiTerdaftar.length > 0) ?
                            komponen.daftarRekomendasiTerdaftar
                            :
                            [{
                                keterangan: ""
                            }],
                        submitableTemuandanRekomendasi: [true,false],
                    }
                )
            });

        this.setState({
            daftarHistoriTemuan: daftarHistoriTemuan,
            riskLevelOptionList: riskLevelList,
            daftarRisikoKategori1: risikoKategori1,
            daftarRisikoKategori2: risikoKategori2,
            daftarRisikoKategori3: risikoKategori3,
            daftarKomponenPemeriksaan: daftarKomponen,
            idCurrentStatus: idCurrentStatus
        })
    }

    getSOPButton(indexKomponen) {
        return (
            <SirioButton
                purple
                hyperlinkLeft
                onClick={() => window.location.href = this.state.daftarKomponenPemeriksaan[indexKomponen].risiko.linkSop}
            >
                {this.state.daftarKomponenPemeriksaan[indexKomponen].risiko.namaSop}
            </SirioButton>
        )
    }

    getHistoriTemuanButton(idRisiko) {
        const histori = <p>
            {this.state.daftarHistoriTemuan
                .filter(x => x.idRisiko === idRisiko)
                .map((temuan, index) =>
                    <p className="text-center p-0 m-0">{index+1}. {temuan.keterangan} </p>
                )}
        </p>;

        return (
            <SirioConfirmButton
                purple recommended
                classes="m-1"
                modalTitle= "Riwayat Temuan Risiko"
                modalDesc={histori}
                customConfirmText=" "
                confirmDisable
            >
                Riwayat Temuan
            </SirioConfirmButton>
        )
    }

    getInputDefinition(indexKomponen) {
        const daftarKomponenPemeriksaan = this.state.daftarKomponenPemeriksaan;
        const riskLevelOptionList = this.state.riskLevelOptionList;
        return (
            [
                {
                    label: "Komponen Risiko",
                    customInput: daftarKomponenPemeriksaan[indexKomponen].risiko.nama
                }, {
                    label: "SOP",
                    customInput: this.getSOPButton(indexKomponen)
                }, {
                    label: "Deskripsi",
                    customInput: <p style={{ whiteSpace: 'pre-line' }}>{daftarKomponenPemeriksaan[indexKomponen].risiko.deskripsi}</p>
                }, {
                    label: "Metodologi",
                    customInput: <p style={{ whiteSpace: 'pre-line' }}>{daftarKomponenPemeriksaan[indexKomponen].risiko.metodologi}</p>
                }, {
                    label: "Ketentuan Sampel",
                    customInput: daftarKomponenPemeriksaan[indexKomponen].risiko.ketentuanSampel?
                        <p style={{ whiteSpace: 'pre-line' }}>{daftarKomponenPemeriksaan[indexKomponen].risiko.ketentuanSampel}</p>
                        :
                        <p>N/A</p>
                }, {
                    label: "Jumlah Populasi",
                    handleChange: (event) => this.handleChangeKomponen(event, indexKomponen),
                    type: "number",
                    name: "jumlahPopulasi",
                    min: 0,
                    value: daftarKomponenPemeriksaan[indexKomponen].jumlahPopulasi,
                    errormessage: daftarKomponenPemeriksaan[indexKomponen].errorJumlahPopulasi,
                    placeholder: "0"
                }, {
                    label: "Jumlah Sampel",
                    handleChange: (event) => this.handleChangeKomponen(event, indexKomponen),
                    type: "number",
                    name: "jumlahSampel",
                    min: 0,
                    value: daftarKomponenPemeriksaan[indexKomponen].jumlahSampel,
                    errormessage: daftarKomponenPemeriksaan[indexKomponen].errorJumlahSampel,
                    placeholder: "0"
                }, {
                    label: "Jumlah Sampel Error",
                    handleChange: (event) => this.handleChangeKomponen(event, indexKomponen),
                    type: "number",
                    name: "jumlahSampelError",
                    min: 0,
                    value: daftarKomponenPemeriksaan[indexKomponen].jumlahSampelError,
                    errormessage: daftarKomponenPemeriksaan[indexKomponen].errorJumlahSampelError,
                    placeholder: "0"
                }, {
                    label: "Keterangan Sampel",
                    handleChange: (event) => this.handleChangeKomponen(event, indexKomponen),
                    type: "textarea",
                    name: "keteranganSampel",
                    value: daftarKomponenPemeriksaan[indexKomponen].keteranganSampel,
                    errormessage: daftarKomponenPemeriksaan[indexKomponen].errorKeteranganSampel,
                    placeholder: "Keterangan sampel"
                }, {
                    label: "Risk Level",
                    handleChange: (name, event) => this.handleSelectChangeKomponen(name, event, indexKomponen),
                    type: "select",
                    name: "idRiskLevel",
                    value: daftarKomponenPemeriksaan[indexKomponen].idRiskLevel,
                    optionList: riskLevelOptionList,
                }, {
                    label: "Hasil Temuan",
                    multiple: true,
                    isMultipleObject: true,
                    handleChange: (event, index) => this.handleMultiFieldChange(event, index, indexKomponen, "keterangan"),
                    type: "textArea",
                    name: "daftarTemuanRisiko",
                    value: daftarKomponenPemeriksaan[indexKomponen].daftarTemuanRisiko.map(temuan => temuan.keterangan),
                    modifier: (name, newField, index) => this.modifyFieldCount(name, newField, index, indexKomponen, "keterangan"),
                }, {
                    label: "",
                    customInput: this.getHistoriTemuanButton(daftarKomponenPemeriksaan[indexKomponen].risiko.id)
                }, {
                    label: "Rekomendasi",
                    multiple: true,
                    isMultipleObject: true,
                    handleChange: (event, index) => this.handleMultiFieldChange(event, index, indexKomponen, "keterangan"),
                    type: "textArea",
                    name: "daftarRekomendasi",
                    value: daftarKomponenPemeriksaan[indexKomponen].daftarRekomendasi.map(rekomendasi => rekomendasi.keterangan),
                    modifier: (name, newField, index) => this.modifyFieldCount(name, newField, index, indexKomponen, "keterangan"),
                }
            ]
        )
    }

    getForm() {
        if(this.state.daftarKomponenPemeriksaan.length > 0) {
            if (this.state.kategoriType === "1") {
                return (
                    this.state.daftarKomponenPemeriksaan
                        .reduce((accumulator, currentValue, currentIndex) => {
                            if(currentValue.risiko.grantParent === this.state.filterKategori ||
                                currentValue.risiko.parent === this.state.filterKategori) {
                                accumulator.push(
                                    <SirioForm
                                        key={currentIndex}
                                        noHeader
                                        isInnerForm
                                        inputDefinition={this.getInputDefinition(currentIndex)}
                                    />
                                )
                            }
                            return accumulator
                        }, [])
                )
            } else if (this.state.kategoriType === "2") {
                return (
                    this.state.daftarKomponenPemeriksaan
                        .reduce((accumulator, currentValue, currentIndex) => {
                            if(currentValue.risiko.parent === this.state.filterKategori) {
                                accumulator.push(
                                    <SirioForm
                                        key={currentIndex}
                                        noHeader
                                        isInnerForm
                                        inputDefinition={this.getInputDefinition(currentIndex)}
                                    />
                                )
                            }
                            return accumulator
                        }, [])
                )
            } else if (this.state.kategoriType === "3") {
                return (
                    this.state.daftarKomponenPemeriksaan
                        .reduce((accumulator, currentValue, currentIndex) => {
                            if(currentValue.risiko.id === this.state.filterKategori) {
                                accumulator.push(
                                    <SirioForm
                                        key={currentIndex}
                                        noHeader
                                        isInnerForm
                                        inputDefinition={this.getInputDefinition(currentIndex)}
                                    />
                                )
                            }
                            return accumulator
                        }, [])
                )
            } else {
                return (
                    this.state.daftarKomponenPemeriksaan
                        .map((komponen, index) => {
                            return (
                                <SirioForm
                                    key={index}
                                    noHeader
                                    isInnerForm
                                    inputDefinition={this.getInputDefinition(index)}
                                />
                            )
                        })
                )
            }
        } else {
            return (
                []
            )
        }
    }

    getBetween() {
        return (
            <>
                <div className="col-md-3 pl-0">
                    <SirioField
                        type="select"
                        handleChange={this.handleSelectChangeRisiko}
                        classes="p-1"
                        name="kategoriType"
                        value={this.state.kategoriType}
                        optionList={
                            [
                                {
                                    label: "Semua",
                                    value: "0"
                                },
                                {
                                    label: "Kategori 1",
                                    value: "1"
                                },
                                {
                                    label: "Kategori 2",
                                    value: "2"
                                },
                                {
                                    label: "Kategori 3",
                                    value: "3"
                                }
                            ]
                        }
                    />
                </div>
                <div className="col-md-3 pl-0">
                    <SirioField
                        type="select"
                        handleChange={this.handleSelectChange}
                        classes="p-1"
                        name="filterKategori"
                        value={this.state.filterKategori}
                        optionList={this.state.riskOptionList}
                    />
                </div>
            </>
        )
    }

    modifyFieldCount(name, newField, index, indexKomponen, objectKey) {
        const array = this.state.daftarKomponenPemeriksaan;
        if (newField.length > array[indexKomponen][name].length) {
            array[indexKomponen][name].push({
                [objectKey]: newField[newField.length - 1]
            })
        } else {
            array[indexKomponen][name].splice(index, 1)
        }

        if (name === "daftarTemuanRisiko" || name === "daftarRekomendasi") {
            array[indexKomponen]["submitableTemuandanRekomendasi"] = [true, true];
        }

        this.setState({
            daftarKomponenPemeriksaan: array
        });
    }

    handleMultiFieldChange(event, index, indexKomponen, objectKey) {
        const targetName = event.target.name;
        const targetValue = event.target.value;
        const array = this.state.daftarKomponenPemeriksaan;
        array[indexKomponen][targetName][index][objectKey] = targetValue;

        if (targetName === "daftarTemuanRisiko" || targetName === "daftarRekomendasi") {
            array[indexKomponen]["submitableTemuandanRekomendasi"] = [true, true];
        }

        this.setState({
            daftarKomponenPemeriksaan: array
        });
    }

    handleChangeKomponen(event, indexKomponen) {
        const { name, value } = event.target;
        const array = this.state.daftarKomponenPemeriksaan;
        array[indexKomponen][name] = value;

        this.setState({
            daftarKomponenPemeriksaan: array
        });

        switch (name) {
            case "jumlahSampel":
                this.validateJumlahSampel(value, indexKomponen);
                break;
            case "keteranganSampel":
                this.validateKeteranganSampel(value, indexKomponen);
                break;
            case "jumlahPopulasi":
                this.validateJumlahPopulasi(value, indexKomponen);
                break;
            case "jumlahSampelError":
                this.validateJumlahSampelError(value, indexKomponen);
                break;
        }
    }

    handleSelectChangeKomponen(name, event, indexKomponen) {
        const array = this.state.daftarKomponenPemeriksaan;
        array[indexKomponen][name] = event.value;

        this.setState({
            daftarKomponenPemeriksaan: array
        });

        if (name === "idRiskLevel") this.validateRiskLevel(event.value, indexKomponen);
    }

    handleSelectChangeRisiko(name, event) {
        const risiko1 = this.state.daftarRisikoKategori1;
        const risiko2 = this.state.daftarRisikoKategori2;
        const risiko3 = this.state.daftarRisikoKategori3;
        if (event.value === "1") {
            this.setState(
                {
                    [name]
                        : event.value,
                    riskOptionList: risiko1,
                    filterKategori: risiko1[0].value
                }
            )
        } else if (event.value === "2") {
            this.setState(
                {
                    [name]
                        : event.value,
                    riskOptionList: risiko2,
                    filterKategori: risiko2[0].value
                }
            )
        } else if (event.value === "3") {
            this.setState(
                {
                    [name]
                        : event.value,
                    riskOptionList: risiko3,
                    filterKategori: risiko3[0].value
                }
            )
        } else {
            this.setState(
                {
                    [name]
                        : event.value,
                    riskOptionList: [
                        {
                            label: "Semua",
                            value: "-1"
                        }
                    ],
                    filterKategori: "-1"
                }
            )
        }
    }

    handleSelectChange(name, event) {
        this.setState(
            {
                [name]
                    : event.value,
            }
        )
    }

    handleSubmit(event, status) {
        event&&event.preventDefault();
        const hasilPemeriksaan = {
            id: this.props.location.state.id,
            idStatus: status,
            daftarKomponenPemeriksaan: this.state.daftarKomponenPemeriksaan.map(komponen => {
                const daftarTemuan = [];
                const daftarTemuanTerdaftar = [];
                const daftarRekomendasi = [];
                const daftarRekomendasiTerdaftar = [];
                komponen.daftarTemuanRisiko.map(temuan => {
                    if (temuan.id != null) {
                        daftarTemuanTerdaftar.push(temuan)
                    } else {
                        daftarTemuan.push(temuan)
                    } return null
                });
                komponen.daftarRekomendasi.map(rekomendasi => {
                    if (rekomendasi.id != null) {
                        daftarRekomendasiTerdaftar.push(rekomendasi)
                    } else {
                        daftarRekomendasi.push(rekomendasi)
                    } return null
                });
                return (
                    {
                        id: komponen.id,
                        idRiskLevel: komponen.idRiskLevel,
                        risiko: komponen.risiko,
                        jumlahSampel: komponen.jumlahSampel,
                        keteranganSampel: komponen.keteranganSampel,
                        daftarTemuanRisiko: daftarTemuan,
                        daftarRekomendasi: daftarRekomendasi,
                        daftarTemuanRisikoTerdaftar: daftarTemuanTerdaftar,
                        daftarRekomendasiTerdaftar: daftarRekomendasiTerdaftar
                    }
                )
            })
        };
        if ((status === 1 && this.submitableDraft()) || ((status === 2 || status === 3) && this.submitable())) {
            HasilPemeriksaanService.editHasilPemeriksaan(hasilPemeriksaan)
                .then(() => this.setRedirect());
        }
    }

    submitable() {
        var submitable = true;
        this.state.daftarKomponenPemeriksaan.map(komponen => {
            submitable = submitable &&
                komponen.submitableRiskLevel[1] &&
                komponen.submitableJumlahPopulasi[1] &&
                komponen.submitableJumlahSampel[1] &&
                komponen.submitableJumlahSampelError[1] &&
                komponen.submitableKeteranganSampel[1] &&
                komponen.submitableTemuandanRekomendasi[1];
            return null
        });
        return submitable;
    }

    submitableDraft() {
        var submitableDraft = true;
        this.state.daftarKomponenPemeriksaan.map(komponen => {
            submitableDraft = submitableDraft &&
                komponen.submitableRiskLevel[0] &&
                komponen.submitableJumlahPopulasi[0] &&
                komponen.submitableJumlahSampel[0] &&
                komponen.submitableJumlahSampelError[0] &&
                komponen.submitableKeteranganSampel[0] &&
                komponen.submitableTemuandanRekomendasi[0];
            return null
        });
        return submitableDraft;
    }

    validateKeteranganSampel(fokusKeteranganSampel, indexKomponen) {
        var submitable = true;
        var submitableDraft = true;
        var errorKeteranganSampel;
        const letter = /.*[a-zA-Z].*/;
        const array = this.state.daftarKomponenPemeriksaan;

        if (fokusKeteranganSampel === null || fokusKeteranganSampel === "") {
            submitable = false;
        } else if (!fokusKeteranganSampel.match(letter)) {
            submitable = false;
            submitableDraft = false;
            errorKeteranganSampel = "Ketarangan perlu mengandung huruf";
        }

        array[indexKomponen]["submitableKeteranganSampel"][1] = submitable;
        array[indexKomponen]["submitableKeteranganSampel"][0] = submitableDraft;
        array[indexKomponen]["errorKeteranganSampel"] = errorKeteranganSampel;
        this.setState({
            daftarKomponenPemeriksaan: array
        });
    }

    validateJumlahPopulasi(fokusJumlahPopulasi, indexKomponen) {
        var submitable = true;
        var submitableDraft = true;
        var errorJumlahPopulasi;
        const number = /^[0-9]*$/;
        const array = this.state.daftarKomponenPemeriksaan;

        if (fokusJumlahPopulasi === null || fokusJumlahPopulasi === "") {
            submitable = false;
        } else if (!fokusJumlahPopulasi.match(number) || parseInt(fokusJumlahPopulasi) < 0) {
            submitable = false;
            submitableDraft = false;
            errorJumlahPopulasi = "Jumlah Populasi berupa angka dan tidak boleh negatif";
        }

        array[indexKomponen]["submitableJumlahPopulasi"][1] = submitable;
        array[indexKomponen]["submitableJumlahPopulasi"][0] = submitableDraft;
        array[indexKomponen]["errorJumlahPopulasi"] = errorJumlahPopulasi;
        this.setState({
            daftarKomponenPemeriksaan: array
        });
    }

    validateJumlahSampel(fokusJumlahSampel, indexKomponen) {
        var submitable = true;
        var submitableDraft = true;
        var errorJumlahSampel;
        const number = /^[0-9]*$/;
        const array = this.state.daftarKomponenPemeriksaan;

        if (fokusJumlahSampel === null || fokusJumlahSampel === "") {
            submitable = false;
        } else if (!fokusJumlahSampel.match(number) || parseInt(fokusJumlahSampel) < 0) {
            submitable = false;
            submitableDraft = false;
            errorJumlahSampel = "Jumlah Sampel berupa angka dan tidak boleh negatif";
        }

        array[indexKomponen]["submitableJumlahSampel"][1] = submitable;
        array[indexKomponen]["submitableJumlahSampel"][0] = submitableDraft;
        array[indexKomponen]["errorJumlahSampel"] = errorJumlahSampel;
        this.setState({
            daftarKomponenPemeriksaan: array
        });
    }

    validateJumlahSampelError(fokusJumlahSampelError, indexKomponen) {
        var submitable = true;
        var submitableDraft = true;
        var errorJumlahSampelError;
        const number = /^[0-9]*$/;
        const array = this.state.daftarKomponenPemeriksaan;

        if (fokusJumlahSampelError === null || fokusJumlahSampelError === "") {
            submitable = false;
        } else if (!fokusJumlahSampelError.match(number) || parseInt(fokusJumlahSampelError) < 0) {
            submitable = false;
            submitableDraft = false;
            errorJumlahSampelError = "Jumlah Sampel Error berupa angka dan tidak boleh negatif";
        }

        array[indexKomponen]["submitableJumlahSampelError"][1] = submitable;
        array[indexKomponen]["submitableJumlahSampelError"][0] = submitableDraft;
        array[indexKomponen]["errorJumlahSampelError"] = errorJumlahSampelError;
        this.setState({
            daftarKomponenPemeriksaan: array
        });
    }

    validateRiskLevel(fokusRiskLevel, indexKomponen) {
        var submitable = true;
        const array = this.state.daftarKomponenPemeriksaan;

        if (fokusRiskLevel === null || fokusRiskLevel === "") {
            submitable = false;
        }

        array[indexKomponen]["submitableRiskLevel"][1] = submitable;
        array[indexKomponen]["submitableRiskLevel"][0] = true;
        this.setState({
            daftarKomponenPemeriksaan: array
        });
    }

    submitButton() {
        var tombolSimpan =
            <SirioButton
                purple
                disabled
                classes="mx-1"
            >
                Simpan
            </SirioButton>;
        if (this.submitable()) {
            tombolSimpan =
                <SirioButton
                    purple
                    recommended
                    classes="mx-1"
                    onClick={(event) => this.handleSubmit(event, 2)}
                >
                    Simpan
                </SirioButton>
        }
        var tombolDraft =
            <SirioButton
                purple
                disabled
                classes="mx-1"
            >
                Draft
            </SirioButton>;
        if (this.state.idCurrentStatus === 1 ? this.submitableDraft() : this.submitable()) {
            tombolDraft =
                <SirioButton
                    purple
                    recommended
                    classes="mx-1"
                    onClick={(event)  => this.handleSubmit(event, 1)}
                >
                    Draft
                </SirioButton>
        }
        return (
            <div>
                {tombolSimpan}
                {tombolDraft}
                <NavLink to={{
                    pathname: "/hasil-pemeriksaan/detail",
                    state: {
                        id: this.props.location.state.id
                    }
                }}>
                    <SirioButton
                        purple
                    >
                        Batal
                    </SirioButton>
                </NavLink>
            </div>
        )
    }

    // Fungsi render SirioForm
    render() {
        return (
            <>
                {this.renderRedirect()}
                <SirioComponentHeader
                    title="Form Ubah Hasil Pemeriksaan"
                    betweenTitleSubtitle={this.getBetween()}
                />

                <ComponentWrapper>
                    {this.getForm().map(e => e)}
                    <div className="w-100 text-right">
                        {this.submitButton()}
                    </div>
                </ComponentWrapper>
            </>
        );
    }
}

export default withRouter(HasilPemeriksaanFormUbah);